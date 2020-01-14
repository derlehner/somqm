package at.ac.tuwien.big.ame.somqm.server.service;

import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.CreateUmlModelRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.SearchUmlModelsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelDetails;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelSummary;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelUml2Content;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UpdateUmlModelRequest;
import at.ac.tuwien.big.ame.somqm.server.model.UmlModel;
import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;
import java.util.List;
import java.util.NoSuchElementException;

public interface UmlModelService {

  UmlModel create(CreateUmlModelRequest request)
      throws IllegalArgumentException, NoSuchElementException;

  void update(long id, UpdateUmlModelRequest request)
      throws IllegalArgumentException, NoSuchElementException;

  /**
   * Per project only one model per type (e.g. class-diagram) is permitted.
   */
  void updateContent(long id, byte[] content)
      throws IllegalArgumentException, NoSuchElementException, ServiceException;

  void delete(long id) throws NoSuchElementException;

  List<UmlModelSummary> search(SearchUmlModelsRequest request) throws IllegalArgumentException;

  UmlModel get(long id) throws NoSuchElementException;

  UmlModelDetails getDetails(long id) throws NoSuchElementException;

  byte[] getContent(long id) throws NoSuchElementException;

  List<UmlModelType> getSupportedTypes();

  UmlModelUml2Content loadModel(long id) throws IllegalArgumentException, NoSuchElementException;

  UmlModelUml2Content loadModelFromProject(long projectId, UmlModelType type)
      throws IllegalArgumentException, NoSuchElementException;
}
