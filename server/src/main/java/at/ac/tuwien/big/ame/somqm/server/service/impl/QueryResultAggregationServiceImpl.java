package at.ac.tuwien.big.ame.somqm.server.service.impl;

import at.ac.tuwien.big.ame.somqm.server.dto.query.GetAllResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetEntitiesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetPackagesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetRelationsResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.aggregation.CountQueryResultAggregation;
import at.ac.tuwien.big.ame.somqm.server.dto.query.aggregation.QueryResultAggregationType;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActionsResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActivitiesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetNodesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetAttributesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetOperationsResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.GetFunctionCallsResult;
import at.ac.tuwien.big.ame.somqm.server.service.QueryResultAggregationService;
import at.ac.tuwien.big.ame.somqm.server.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryResultAggregationServiceImpl implements QueryResultAggregationService {

  @Autowired
  public QueryResultAggregationServiceImpl() {

  }

  @Override
  public long aggregateAll(GetAllResult result, QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException {
    return aggregateRelations(result, resultAggregator);
  }

  @Override
  public long aggregatePackages(GetPackagesResult result,
      QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException {
    if (result == null) {
      throw new IllegalArgumentException("Supplied result is invalid");
    }
    if (resultAggregator == null) {
      throw new IllegalArgumentException("Supplied result-aggregator is invalid");
    }
    if (resultAggregator instanceof CountQueryResultAggregation) {
      return result.getPackages().size();
    }
    throw new ServiceException("Supplied result-aggregator not implemented");
  }

  @Override
  public long aggregateEntities(GetEntitiesResult result,
      QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException {
    if (result == null) {
      throw new IllegalArgumentException("Supplied result is invalid");
    }
    if (resultAggregator == null) {
      throw new IllegalArgumentException("Supplied result-aggregator is invalid");
    }
    if (resultAggregator instanceof CountQueryResultAggregation) {
      return result.getEntities().size();
    }
    throw new ServiceException("Supplied result-aggregator not implemented");
  }

  @Override
  public long aggregateRelations(GetRelationsResult result,
      QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException {
    if (result == null) {
      throw new IllegalArgumentException("Supplied result is invalid");
    }
    if (resultAggregator == null) {
      throw new IllegalArgumentException("Supplied result-aggregator is invalid");
    }
    if (resultAggregator instanceof CountQueryResultAggregation) {
      return result.getRelations().size();
    }
    throw new ServiceException("Supplied result-aggregator not implemented");
  }

  @Override
  public long aggregateAttributes(GetAttributesResult result,
      QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException {
    if (result == null) {
      throw new IllegalArgumentException("Supplied result is invalid");
    }
    if (resultAggregator == null) {
      throw new IllegalArgumentException("Supplied result-aggregator is invalid");
    }
    if (resultAggregator instanceof CountQueryResultAggregation) {
      return result.getAttributes().size();
    }
    throw new ServiceException("Supplied result-aggregator not implemented");
  }

  @Override
  public long aggregateOperations(GetOperationsResult result,
      QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException {
    if (result == null) {
      throw new IllegalArgumentException("Supplied result is invalid");
    }
    if (resultAggregator == null) {
      throw new IllegalArgumentException("Supplied result-aggregator is invalid");
    }
    if (resultAggregator instanceof CountQueryResultAggregation) {
      return result.getOperations().size();
    }
    throw new ServiceException("Supplied result-aggregator not implemented");
  }

  @Override
  public long aggregateActivities(GetActivitiesResult result,
      QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException {
    if (result == null) {
      throw new IllegalArgumentException("Supplied result is invalid");
    }
    if (resultAggregator == null) {
      throw new IllegalArgumentException("Supplied result-aggregator is invalid");
    }
    if (resultAggregator instanceof CountQueryResultAggregation) {
      return result.getActivities().size();
    }
    throw new ServiceException("Supplied result-aggregator not implemented");
  }

  @Override
  public long aggregateActions(GetActionsResult result, QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException {
    if (result == null) {
      throw new IllegalArgumentException("Supplied result is invalid");
    }
    if (resultAggregator == null) {
      throw new IllegalArgumentException("Supplied result-aggregator is invalid");
    }
    if (resultAggregator instanceof CountQueryResultAggregation) {
      return result.getActions().size();
    }
    throw new ServiceException("Supplied result-aggregator not implemented");
  }

  @Override
  public long aggregateNodes(GetNodesResult result, QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException {
    if (result == null) {
      throw new IllegalArgumentException("Supplied result is invalid");
    }
    if (resultAggregator == null) {
      throw new IllegalArgumentException("Supplied result-aggregator is invalid");
    }
    if (resultAggregator instanceof CountQueryResultAggregation) {
      return result.getNodes().size();
    }
    throw new ServiceException("Supplied result-aggregator not implemented");
  }

  @Override
  public long aggregateFunctionCalls(GetFunctionCallsResult result,
      QueryResultAggregationType resultAggregator)
      throws IllegalArgumentException, ServiceException {
    if (result == null) {
      throw new IllegalArgumentException("Supplied result is invalid");
    }
    if (resultAggregator == null) {
      throw new IllegalArgumentException("Supplied result-aggregator is invalid");
    }
    if (resultAggregator instanceof CountQueryResultAggregation) {
      return result.getFunctionCalls().size();
    }
    throw new ServiceException("Supplied result-aggregator not implemented");
  }
}
