package at.ac.tuwien.big.ame.somqm.server.service.impl;

import at.ac.tuwien.big.ame.somqm.server.dao.ProjectDao;
import at.ac.tuwien.big.ame.somqm.server.dao.UmlModelDao;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.CreateUmlModelRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.SearchUmlModelsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelDetails;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelSummary;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelUml2Content;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UpdateUmlModelRequest;
import at.ac.tuwien.big.ame.somqm.server.model.Project;
import at.ac.tuwien.big.ame.somqm.server.model.UmlModel;
import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;
import at.ac.tuwien.big.ame.somqm.server.service.ServiceException;
import at.ac.tuwien.big.ame.somqm.server.service.UmlModelService;
import at.ac.tuwien.big.ame.somqm.server.service.UmlValidationService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalLong;
import org.eclipse.uml2.uml.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UmlModelServiceImpl implements UmlModelService {

  private final ProjectDao projectDao;
  private final UmlModelDao umlModelDao;
  private final UmlValidationService validationService;

  @Autowired
  public UmlModelServiceImpl(ProjectDao projectDao, UmlModelDao umlModelDao,
      UmlValidationService validationService) {
    this.projectDao = projectDao;
    this.umlModelDao = umlModelDao;
    this.validationService = validationService;
  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
  public UmlModel create(CreateUmlModelRequest request)
      throws IllegalArgumentException, NoSuchElementException {
    if (request == null) {
      throw new IllegalArgumentException("Supplied request is invalid");
    }
    if (request.getName() == null || request.getName().trim().isEmpty()) {
      throw new IllegalArgumentException("Supplied name is invalid");
    }
    request.setName(request.getName().trim());
    if (request.getDescription() == null) {
      throw new IllegalArgumentException("Supplied description is invalid");
    }
    request.setDescription(request.getDescription().trim());
    Optional<Project> projectOptional = projectDao.findById(request.getProjectId());
    if (!projectOptional.isPresent()) {
      throw new NoSuchElementException(
          "No project found with supplied id " + request.getProjectId());
    }
    Project project = projectOptional.get();

    UmlModel newUmlModel = new UmlModel();
    newUmlModel.setName(request.getName());
    newUmlModel.setDescription(request.getDescription());
    newUmlModel.setType(null);
    newUmlModel.setContent(null);
    newUmlModel.setProject(project);
    return umlModelDao.save(newUmlModel);
  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
  public void update(long id, UpdateUmlModelRequest request)
      throws IllegalArgumentException, NoSuchElementException {
    if (request == null) {
      throw new IllegalArgumentException("Supplied request is invalid");
    }
    if (request.getName() == null || request.getName().trim().isEmpty()) {
      throw new IllegalArgumentException("Supplied name is invalid");
    }
    request.setName(request.getName().trim());
    if (request.getDescription() == null) {
      throw new IllegalArgumentException("Supplied description is invalid");
    }
    request.setDescription(request.getDescription().trim());
    Optional<UmlModel> existingUmlModelOptional = umlModelDao.findById(id);
    if (!existingUmlModelOptional.isPresent()) {
      throw new NoSuchElementException("No uml-model found with supplied id " + id);
    }
    UmlModel existingUmlModel = existingUmlModelOptional.get();

    existingUmlModel.setName(request.getName());
    existingUmlModel.setDescription(request.getDescription());
    umlModelDao.save(existingUmlModel);
  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
  public void updateContent(long id, byte[] content)
      throws IllegalArgumentException, NoSuchElementException, ServiceException {
    if (content == null) {
      throw new IllegalArgumentException("Supplied content is invalid");
    }
    if (content.length == 0) {
      throw new IllegalArgumentException("Supplied content is empty");
    }

    Optional<UmlModel> umlModelOptional = umlModelDao.findById(id);
    if (!umlModelOptional.isPresent()) {
      throw new NoSuchElementException("No uml-model found with supplied id " + id);
    }
    UmlModel existingUmlModel = umlModelOptional.get();

    Model model = validationService.load(content);

    UmlModelType modelType = validationService.validate(model, this.getSupportedTypes());

    OptionalLong idUmlModelSameTypeAndProject = umlModelDao
        .findIdByTypeAndProject(modelType, existingUmlModel.getProject().getId());
    if (idUmlModelSameTypeAndProject.isPresent()
        && idUmlModelSameTypeAndProject.getAsLong() != existingUmlModel.getId()) {
      throw new IllegalArgumentException(
          "Uml-model from same type '" + modelType + "' already exists in the project");
    }

    Map<byte[], UmlModelType> otherModelsData = umlModelDao
        .findContentByProject(existingUmlModel.getProject().getId(), existingUmlModel.getId());
    Map<Model, UmlModelType> otherModels = new HashMap<>();
    for (Map.Entry<byte[], UmlModelType> otherModel : otherModelsData.entrySet()) {
      Model m = validationService.load(otherModel.getKey());
      otherModels.put(m, otherModel.getValue());
    }
    validationService.verifyConsistency(model, modelType, otherModels);

    existingUmlModel.setType(modelType);
    existingUmlModel.setContent(content);
    umlModelDao.save(existingUmlModel);
  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
  public void delete(long id) throws NoSuchElementException {
    Optional<UmlModel> umlModelOptional = umlModelDao.findById(id);
    if (!umlModelOptional.isPresent()) {
      throw new NoSuchElementException("No uml-model found with supplied id " + id);
    }
    UmlModel umlModel = umlModelOptional.get();
    umlModelDao.delete(umlModel);
  }

  @Override
  public List<UmlModelSummary> search(SearchUmlModelsRequest request)
      throws IllegalArgumentException {
    if (request == null) {
      throw new IllegalArgumentException("Supplied request is invalid");
    }
    return umlModelDao.findAll(request.getNameToSearch(), request.getTypeToSearch(),
        request.getProjectIdToSearch());
  }

  @Override
  public UmlModel get(long id) throws NoSuchElementException {
    Optional<UmlModel> umlModelOptional = umlModelDao.findById(id);
    if (!umlModelOptional.isPresent()) {
      throw new NoSuchElementException("No uml-model found with supplied id " + id);
    }
    return umlModelOptional.get();
  }

  @Override
  public UmlModelDetails getDetails(long id) throws NoSuchElementException {
    Optional<UmlModelDetails> umlModelDetailsOptional = umlModelDao.findDetailsById(id);
    if (!umlModelDetailsOptional.isPresent()) {
      throw new NoSuchElementException("No uml-model found with supplied id " + id);
    }
    return umlModelDetailsOptional.get();
  }

  @Override
  public byte[] getContent(long id) throws NoSuchElementException {
    Optional<UmlModel> umlModelOptional = umlModelDao.findById(id);
    if (!umlModelOptional.isPresent()) {
      throw new NoSuchElementException("No uml-model found with supplied id " + id);
    }
    UmlModel umlModel = umlModelOptional.get();
    byte[] content = umlModel.getContent();
    if (content == null) {
      throw new NoSuchElementException("Uml-model has no content set");
    }
    return content;
  }

  @Override
  public List<UmlModelType> getSupportedTypes() {
    return new ArrayList<>(Arrays.asList(UmlModelType.values()));
  }

  @Override
  public UmlModelUml2Content loadModel(long id)
      throws IllegalArgumentException, NoSuchElementException {
    UmlModel umlModel = get(id);
    byte[] content = umlModel.getContent();
    if (content == null) {
      throw new NoSuchElementException("Uml-model has no content set");
    }
    UmlModelType type = umlModel.getType();
    Model model = validationService.load(content);
    return new UmlModelUml2Content(model, type);
  }

  @Override
  public UmlModelUml2Content loadModelFromProject(long projectId, UmlModelType type)
      throws IllegalArgumentException, NoSuchElementException {
    if (type == null) {
      throw new IllegalArgumentException("Supplied type is invalid");
    }
    SearchUmlModelsRequest searchUmlModelsRequest = new SearchUmlModelsRequest();
    searchUmlModelsRequest.setTypeToSearch(type);
    searchUmlModelsRequest.setProjectIdToSearch(projectId);
    List<UmlModelSummary> searchResult = search(searchUmlModelsRequest);
    if (searchResult.isEmpty()) {
      throw new NoSuchElementException(
          "No uml-model found with supplied project-id " + projectId + " and type '" + type + "'");
    }
    long modelId = searchResult.get(0).getId();
    return loadModel(modelId);
  }
}
