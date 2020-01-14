package at.ac.tuwien.big.ame.somqm.server.rest;

import at.ac.tuwien.big.ame.somqm.server.dto.query.GetAllAggregationRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetAllRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetAllResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetEntitiesAggregationRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetEntitiesRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetEntitiesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetFinalStateRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetFinalStateResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetInitialStateRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetInitialStateResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetPackagesAggregationRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetPackagesRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetPackagesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetRelationsAggregationRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetRelationsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetRelationsResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActionsAggregationRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActionsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActionsResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActivitiesAggregationRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActivitiesRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActivitiesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetNodesAggregationRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetNodesRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetNodesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetAttributesAggregationRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetAttributesRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetAttributesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetOperationsAggregationRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetOperationsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetOperationsResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.GetFunctionCallsAggregationRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.GetFunctionCallsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.GetFunctionCallsResult;
import at.ac.tuwien.big.ame.somqm.server.service.QueryResultAggregationService;
import at.ac.tuwien.big.ame.somqm.server.service.ServiceException;
import at.ac.tuwien.big.ame.somqm.server.service.UmlGenericQueryService;
import at.ac.tuwien.big.ame.somqm.server.service.UmlTypedQueryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/queries")
public class QueryController {

  public static final String SWAGGER_REGEXFILTER_EXAMPLE = "^.*$";

  private final UmlGenericQueryService genericQueryService;
  private final UmlTypedQueryService typedQueryService;
  private final QueryResultAggregationService aggregationService;

  @Autowired
  public QueryController(UmlGenericQueryService genericQueryService,
      UmlTypedQueryService typedQueryService, QueryResultAggregationService aggregationService) {
    this.genericQueryService = genericQueryService;
    this.typedQueryService = typedQueryService;
    this.aggregationService = aggregationService;
  }

  @PostMapping(value = "/all")
  @ApiOperation(value = "Returns relation-infos with type-specific infos of corresponding entities")
  public GetAllResult getAll(@RequestBody GetAllRequest request) throws ServiceException {
    return genericQueryService.getAll(request);
  }

  @PostMapping(value = "/all/aggregate")
  @ApiOperation(value = "Returns infos about aggregated model-tree")
  public long getAllAggregation(@RequestBody GetAllAggregationRequest request)
      throws ServiceException {
    GetAllResult result = genericQueryService.getAll(request);
    return aggregationService.aggregateAll(result, request.getAggregationType());
  }

  @PostMapping(value = "/packages")
  @ApiOperation(value = "Returns package-infos")
  public GetPackagesResult getPackages(@RequestBody GetPackagesRequest request) {
    return genericQueryService.getPackages(request);
  }

  @PostMapping(value = "/packages/aggregate")
  @ApiOperation(value = "Returns infos about aggregated packages")
  public long getPackagesAggregation(@RequestBody GetPackagesAggregationRequest request)
      throws ServiceException {
    GetPackagesResult result = genericQueryService.getPackages(request);
    return aggregationService.aggregatePackages(result, request.getAggregationType());
  }

  @PostMapping(value = "/entities")
  @ApiOperation(value = "Returns entity-infos")
  public GetEntitiesResult getEntities(@RequestBody GetEntitiesRequest request)
      throws ServiceException {
    return genericQueryService.getEntities(request);
  }

  @PostMapping(value = "/entities/aggregate")
  @ApiOperation(value = "Returns infos about aggregated entities")
  public long getEntitiesAggregation(@RequestBody GetEntitiesAggregationRequest request)
      throws ServiceException {
    GetEntitiesResult result = genericQueryService.getEntities(request);
    return aggregationService.aggregateEntities(result, request.getAggregationType());
  }

  @PostMapping(value = "/relations")
  @ApiOperation(value = "Returns relation-infos")
  public GetRelationsResult getRelations(@RequestBody GetRelationsRequest request)
      throws ServiceException {
    return genericQueryService.getRelations(request);
  }

  @PostMapping(value = "/relations/aggregate")
  @ApiOperation(value = "Returns infos about aggregated relations")
  public long getRelationsAggregation(@RequestBody GetRelationsAggregationRequest request)
      throws ServiceException {
    GetRelationsResult result = genericQueryService.getRelations(request);
    return aggregationService.aggregateRelations(result, request.getAggregationType());
  }

  @PostMapping(value = "/attributes")
  @ApiOperation(value = "Returns attribute-infos")
  public GetAttributesResult getAttributes(@RequestBody GetAttributesRequest request) {
    return typedQueryService.getAttributes(request);
  }

  @PostMapping(value = "/attributes/aggregate")
  @ApiOperation(value = "Returns infos about aggregated attributes")
  public long getAttributesAggregation(@RequestBody GetAttributesAggregationRequest request)
      throws ServiceException {
    GetAttributesResult result = typedQueryService.getAttributes(request);
    return aggregationService.aggregateAttributes(result, request.getAggregationType());
  }

  @PostMapping(value = "/operations")
  @ApiOperation(value = "Returns operation-infos")
  public GetOperationsResult getOperations(@RequestBody GetOperationsRequest request) {
    return typedQueryService.getOperations(request);
  }

  @PostMapping(value = "/operations/aggregate")
  @ApiOperation(value = "Returns infos about aggregated operations")
  public long getOperationsAggregation(@RequestBody GetOperationsAggregationRequest request)
      throws ServiceException {
    GetOperationsResult result = typedQueryService.getOperations(request);
    return aggregationService.aggregateOperations(result, request.getAggregationType());
  }

  @PostMapping(value = "/activities")
  @ApiOperation(value = "Returns activity-infos")
  public GetActivitiesResult getActivities(@RequestBody GetActivitiesRequest request) {
    return typedQueryService.getActivities(request);
  }

  @PostMapping(value = "/activities/aggregate")
  @ApiOperation(value = "Returns infos about aggregated activities")
  public long getActivitiesAggregation(@RequestBody GetActivitiesAggregationRequest request)
      throws ServiceException {
    GetActivitiesResult result = typedQueryService.getActivities(request);
    return aggregationService.aggregateActivities(result, request.getAggregationType());
  }

  @PostMapping(value = "/actions")
  @ApiOperation(value = "Returns action-infos")
  public GetActionsResult getActions(@RequestBody GetActionsRequest request)
      throws ServiceException {
    return typedQueryService.getActions(request);
  }

  @PostMapping(value = "/actions/aggregate")
  @ApiOperation(value = "Returns infos about aggregated actions")
  public long getActionsAggregation(@RequestBody GetActionsAggregationRequest request)
      throws ServiceException {
    GetActionsResult result = typedQueryService.getActions(request);
    return aggregationService.aggregateActions(result, request.getAggregationType());
  }

  @PostMapping(value = "/nodes")
  @ApiOperation(value = "Returns node-infos")
  public GetNodesResult getNodes(@RequestBody GetNodesRequest request) throws ServiceException {
    return typedQueryService.getNodes(request);
  }

  @PostMapping(value = "/nodes/aggregate")
  @ApiOperation(value = "Returns infos about aggregated nodes")
  public long getNodesAggregation(@RequestBody GetNodesAggregationRequest request)
      throws ServiceException {
    GetNodesResult result = typedQueryService.getNodes(request);
    return aggregationService.aggregateNodes(result, request.getAggregationType());
  }

  @PostMapping(value = "/functioncalls")
  @ApiOperation(value = "Returns function-call-infos")
  public GetFunctionCallsResult getFunctionCalls(@RequestBody GetFunctionCallsRequest request) {
    return typedQueryService.getFunctionCalls(request);
  }

  @PostMapping(value = "/functioncalls/aggregate")
  @ApiOperation(value = "Returns infos about aggregated function-calls")
  public long getFunctionCallsAggregation(@RequestBody GetFunctionCallsAggregationRequest request)
      throws ServiceException {
    GetFunctionCallsResult result = typedQueryService.getFunctionCalls(request);
    return aggregationService.aggregateFunctionCalls(result, request.getAggregationType());
  }

  @PostMapping(value = "/states/initial")
  @ApiOperation(value = "Returns infos about the first found initial state")
  public GetInitialStateResult getInitialState(@RequestBody GetInitialStateRequest request)
      throws ServiceException {
    return genericQueryService.getInitialState(request);
  }

  @PostMapping(value = "/states/final")
  @ApiOperation(value = "Returns infos about the first found final state")
  public GetFinalStateResult getFinalState(@RequestBody GetFinalStateRequest request)
      throws ServiceException {
    return genericQueryService.getFinalState(request);
  }

}
