package at.ac.tuwien.big.ame.somqm.server.service.impl;

import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Action;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Activity;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActionsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActionsResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActivitiesRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActivitiesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetNodesRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetNodesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Node;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassAttribute;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassOperation;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetAttributesRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetAttributesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetOperationsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetOperationsResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.FunctionCall;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.GetFunctionCallsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.GetFunctionCallsResult;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelUml2Content;
import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;
import at.ac.tuwien.big.ame.somqm.server.service.ServiceException;
import at.ac.tuwien.big.ame.somqm.server.service.Uml2ExtractorService;
import at.ac.tuwien.big.ame.somqm.server.service.UmlModelService;
import at.ac.tuwien.big.ame.somqm.server.service.UmlTypedQueryService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UmlTypedQueryServiceImpl implements UmlTypedQueryService {

  private UmlModelService umlModelService;
  private Uml2ExtractorService extractorService;

  @Autowired
  public UmlTypedQueryServiceImpl(UmlModelService umlModelService,
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
  public GetAttributesResult getAttributes(GetAttributesRequest request)
      throws IllegalArgumentException, NoSuchElementException {
    if (request == null) {
      throw new IllegalArgumentException("Supplied request is invalid");
    }
    Pattern entityNameFilter = regexToPattern(request.getEntityNameRegex(), true);
    Pattern attributeNameFilter = regexToPattern(request.getAttributeNameRegex(), true);

    UmlModelUml2Content content = umlModelService
        .loadModelFromProject(request.getProjectId(), UmlModelType.CLASS_MODEL);
    List<ClassAttribute> attributes = extractorService
        .extractAttributes(content, entityNameFilter, attributeNameFilter);
    return new GetAttributesResult(attributes);
  }

  @Override
  public GetOperationsResult getOperations(GetOperationsRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("Supplied request is invalid");
    }
    Pattern entityNameFilter = regexToPattern(request.getEntityNameRegex(), true);
    Pattern operationNameFilter = regexToPattern(request.getOperationNameRegex(), true);

    UmlModelUml2Content content = umlModelService
        .loadModelFromProject(request.getProjectId(), UmlModelType.CLASS_MODEL);
    List<ClassOperation> operations = extractorService
        .extractOperations(content, entityNameFilter, operationNameFilter);
    return new GetOperationsResult(operations);
  }

  @Override
  public GetActivitiesResult getActivities(GetActivitiesRequest request)
      throws IllegalArgumentException, NoSuchElementException {
    if (request == null) {
      throw new IllegalArgumentException("Supplied request is invalid");
    }
    Pattern packageNameFilter = regexToPattern(request.getPackageNameRegex(), true);
    Pattern activityNameFilter = regexToPattern(request.getActivityNameRegex(), true);

    UmlModelUml2Content content = umlModelService
        .loadModelFromProject(request.getProjectId(), UmlModelType.ACTIVITY_MODEL);
    List<Activity> activities = extractorService
        .extractActivities(content, packageNameFilter, activityNameFilter);
    return new GetActivitiesResult(activities);
  }

  @Override
  public GetActionsResult getActions(GetActionsRequest request)
      throws IllegalArgumentException, NoSuchElementException, ServiceException {
    if (request == null) {
      throw new IllegalArgumentException("Supplied request is invalid");
    }
    Pattern packageNameFilter = regexToPattern(request.getPackageNameRegex(), true);
    Pattern activityNameFilter = regexToPattern(request.getActivityNameRegex(), true);
    Pattern actionNameFilter = regexToPattern(request.getActionNameRegex(), true);

    UmlModelUml2Content content = umlModelService
        .loadModelFromProject(request.getProjectId(), UmlModelType.ACTIVITY_MODEL);
    List<Action> actions = extractorService
        .extractActions(content, packageNameFilter, activityNameFilter, actionNameFilter);
    return new GetActionsResult(actions);
  }

  @Override
  public GetNodesResult getNodes(GetNodesRequest request)
      throws IllegalArgumentException, NoSuchElementException, ServiceException {
    if (request == null) {
      throw new IllegalArgumentException("Supplied request is invalid");
    }
    Pattern packageNameFilter = regexToPattern(request.getPackageNameRegex(), true);
    Pattern activityNameFilter = regexToPattern(request.getActivityNameRegex(), true);
    Pattern nodeNameFilter = regexToPattern(request.getNodeNameRegex(), true);

    UmlModelUml2Content content = umlModelService
        .loadModelFromProject(request.getProjectId(), UmlModelType.ACTIVITY_MODEL);
    List<Node> nodes = extractorService
        .extractNodes(content, packageNameFilter, activityNameFilter, nodeNameFilter,
            request.getNodeType());
    return new GetNodesResult(nodes);
  }

  @Override
  public GetFunctionCallsResult getFunctionCalls(GetFunctionCallsRequest request)
      throws IllegalArgumentException, NoSuchElementException {
    if (request == null) {
      throw new IllegalArgumentException("Supplied request is invalid");
    }
    Pattern functionCallNameFilter = regexToPattern(request.getFunctionCallNameRegex(), true);

    UmlModelUml2Content content = umlModelService
        .loadModelFromProject(request.getProjectId(), UmlModelType.STATE_MACHINE_MODEL);
    List<FunctionCall> functionCalls = extractorService
        .extractFunctionCalls(content, functionCallNameFilter);
    return new GetFunctionCallsResult(functionCalls);
  }

}
