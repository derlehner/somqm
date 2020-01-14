package at.ac.tuwien.big.ame.somqm.server.service;

import at.ac.tuwien.big.ame.somqm.server.dto.query.GetAllResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetEntitiesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetPackagesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetRelationsResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.aggregation.QueryResultAggregationType;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActionsResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActivitiesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetNodesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetAttributesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetOperationsResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.GetFunctionCallsResult;

public interface QueryResultAggregationService {

  long aggregateAll(GetAllResult result, QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException;

  long aggregatePackages(GetPackagesResult result, QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException;

  long aggregateEntities(GetEntitiesResult result, QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException;

  long aggregateRelations(GetRelationsResult result, QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException;

  long aggregateAttributes(GetAttributesResult result, QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException;

  long aggregateOperations(GetOperationsResult result, QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException;

  long aggregateActivities(GetActivitiesResult result, QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException;

  long aggregateActions(GetActionsResult result, QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException;

  long aggregateNodes(GetNodesResult result, QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException;

  long aggregateFunctionCalls(GetFunctionCallsResult result,
      QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException;

}
