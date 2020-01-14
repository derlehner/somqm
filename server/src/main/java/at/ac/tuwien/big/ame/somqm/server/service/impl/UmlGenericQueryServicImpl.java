package at.ac.tuwien.big.ame.somqm.server.service.impl;

import at.ac.tuwien.big.ame.somqm.server.dto.query.EntityInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetAllRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetAllResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetEntitiesRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetEntitiesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetFinalStateRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetFinalStateResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetInitialStateRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetInitialStateResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetPackagesRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetPackagesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetRelationsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetRelationsResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.RelationInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Node;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.enumeration.NodeType;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.FinalState;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.Pseudostate;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.enumeration.PseudostateType;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelUml2Content;
import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;
import at.ac.tuwien.big.ame.somqm.server.service.ServiceException;
import at.ac.tuwien.big.ame.somqm.server.service.Uml2ExtractorService;
import at.ac.tuwien.big.ame.somqm.server.service.UmlGenericQueryService;
import at.ac.tuwien.big.ame.somqm.server.service.UmlModelService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UmlGenericQueryServicImpl implements UmlGenericQueryService {

  private UmlModelService umlModelService;
  private Uml2ExtractorService extractorService;

  @Autowired
  public UmlGenericQueryServicImpl(UmlModelService umlModelService,
      Uml2ExtractorService extractorService) {
    this.umlModelService = umlModelService;
    this.extractorService = extractorService;
  }

  private static Pattern regexToPattern(String regex, boolean allowNull)
      throws IllegalArgumentException {
    if (regex == null) {
      if (allowNull) {
        return null;
      }
      throw new IllegalArgumentException("Supplied regex is invalid");
    }
    Pattern pattern;
    try {
      pattern = Pattern.compile(regex);
    } catch (PatternSyntaxException e) {
      throw new IllegalArgumentException("Supplied regex is invalid", e);
    }
    return pattern;
  }

  @Override
  public GetAllResult getAll(GetAllRequest request)
      throws IllegalArgumentException, NoSuchElementException, ServiceException {
    if (request == null) {
      throw new IllegalArgumentException("Supplied request is invalid");
    }
    Pattern sourceEntityNameFilter = regexToPattern(request.getSourceEntityNameRegex(), true);
    Pattern targetEntityNameFilter = regexToPattern(request.getTargetEntityNameRegex(), true);

    UmlModelUml2Content content = umlModelService.loadModel(request.getModelId());
    List<RelationInfo> relations = extractorService
        .extractAll(content, sourceEntityNameFilter, targetEntityNameFilter);
    return new GetAllResult(relations);
  }

  @Override
  public GetPackagesResult getPackages(GetPackagesRequest request)
      throws IllegalArgumentException, NoSuchElementException {
    if (request == null) {
      throw new IllegalArgumentException("Supplied request is invalid");
    }
    Pattern packageNameFilter = regexToPattern(request.getPackageNameRegex(), true);

    UmlModelUml2Content content = umlModelService.loadModel(request.getModelId());
    List<Package> packages = extractorService.extractPackages(content, packageNameFilter);
    return new GetPackagesResult(packages);
  }

  @Override
  public GetEntitiesResult getEntities(GetEntitiesRequest request)
      throws IllegalArgumentException, NoSuchElementException, ServiceException {
    if (request == null) {
      throw new IllegalArgumentException("Supplied request is invalid");
    }
    Pattern packageNameFilter = regexToPattern(request.getPackageNameRegex(), true);
    Pattern entityNameFilter = regexToPattern(request.getEntityNameRegex(), true);

    UmlModelUml2Content content = umlModelService.loadModel(request.getModelId());
    List<EntityInfo> entities = extractorService
        .extractEntities(content, request.isIncludeTypeSpecificDetails(), packageNameFilter,
            entityNameFilter);
    return new GetEntitiesResult(entities);
  }

  @Override
  public GetRelationsResult getRelations(GetRelationsRequest request)
      throws IllegalArgumentException, NoSuchElementException, ServiceException {
    if (request == null) {
      throw new IllegalArgumentException("Supplied request is invalid");
    }
    Pattern sourceEntityNameFilter = regexToPattern(request.getSourceEntityNameRegex(), true);
    Pattern targetEntityNameFilter = regexToPattern(request.getTargetEntityNameRegex(), true);

    UmlModelUml2Content content = umlModelService.loadModel(request.getModelId());
    List<RelationInfo> relations = extractorService
        .extractRelations(content, request.isIncludeTypeSpecificDetails(), sourceEntityNameFilter,
            targetEntityNameFilter);
    return new GetRelationsResult(relations);
  }

  @Override
  public GetInitialStateResult getInitialState(GetInitialStateRequest request)
      throws IllegalArgumentException, NoSuchElementException, ServiceException {
    if (request == null) {
      throw new IllegalArgumentException("Supplied request is invalid");
    }

    UmlModelUml2Content content = umlModelService.loadModel(request.getModelId());
    if (content.getType() == UmlModelType.ACTIVITY_MODEL) {
      List<Node> nodes = extractorService
          .extractNodes(content, null, null, null, NodeType.INITIAL);
      if (nodes.isEmpty()) {
        throw new NoSuchElementException("Model has no initial state");
      }
      EntityInfo initialState = nodes.get(0);
      return new GetInitialStateResult(initialState);
    } else if (content.getType() == UmlModelType.STATE_MACHINE_MODEL) {
      List<EntityInfo> entities = extractorService.extractEntities(content, true, null, null);
      for (EntityInfo entity : entities) {
        if (entity instanceof Pseudostate) {
          Pseudostate pseudostateEntity = (Pseudostate) entity;
          if (pseudostateEntity.getType() == PseudostateType.INITIAL) {
            return new GetInitialStateResult(pseudostateEntity);
          }
        }
      }
      throw new NoSuchElementException("Model has no initial state");
    } else {
      throw new IllegalArgumentException(
          "Model with supplied id is not of type '" + UmlModelType.ACTIVITY_MODEL + "' or '"
              + UmlModelType.STATE_MACHINE_MODEL + "'");
    }
  }

  @Override
  public GetFinalStateResult getFinalState(GetFinalStateRequest request)
      throws IllegalArgumentException, NoSuchElementException, ServiceException {
    if (request == null) {
      throw new IllegalArgumentException("Supplied request is invalid");
    }

    UmlModelUml2Content content = umlModelService.loadModel(request.getModelId());
    if (content.getType() == UmlModelType.ACTIVITY_MODEL) {
      List<Node> nodes = extractorService
          .extractNodes(content, null, null, null, NodeType.ACTIVITY_FINAL);
      if (nodes.isEmpty()) {
        throw new NoSuchElementException("Model has no final state");
      }
      EntityInfo finalState = nodes.get(0);
      return new GetFinalStateResult(finalState);
    } else if (content.getType() == UmlModelType.STATE_MACHINE_MODEL) {
      List<EntityInfo> entities = extractorService.extractEntities(content, true, null, null);
      for (EntityInfo entity : entities) {
        if (entity instanceof FinalState) {
          return new GetFinalStateResult(entity);
        }
      }
      throw new NoSuchElementException("Model has no final state");
    } else {
      throw new IllegalArgumentException(
          "Model with supplied id is not of type '" + UmlModelType.ACTIVITY_MODEL + "' or '"
              + UmlModelType.STATE_MACHINE_MODEL + "'");
    }
  }

}
