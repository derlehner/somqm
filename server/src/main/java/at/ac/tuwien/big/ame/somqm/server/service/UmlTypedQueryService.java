package at.ac.tuwien.big.ame.somqm.server.service;

import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActionsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActionsResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActivitiesRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActivitiesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetNodesRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetNodesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetAttributesRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetAttributesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetOperationsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetOperationsResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.GetFunctionCallsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.GetFunctionCallsResult;
import java.util.NoSuchElementException;

public interface UmlTypedQueryService {

  // Class model specific

  /**
   * @throws NoSuchElementException If no project with supplied project-id exists or project
   * contains no class model or if entity-name is supplied but there is no matching entity.
   */
  GetAttributesResult getAttributes(GetAttributesRequest request)
      throws IllegalArgumentException, NoSuchElementException;

  /**
   * @throws NoSuchElementException If no project with supplied project-id exists or project
   * contains no class model or if entity-name is supplied but there is no matching entity.
   */
  GetOperationsResult getOperations(GetOperationsRequest request)
      throws IllegalArgumentException, NoSuchElementException;

  // Activity model specific

  /**
   * @throws NoSuchElementException If no project with supplied project-id exists or project
   * contains no activity model or if package-name is supplied but there is no matching package.
   */
  GetActivitiesResult getActivities(GetActivitiesRequest request)
      throws IllegalArgumentException, NoSuchElementException;

  /**
   * @throws NoSuchElementException If no project with supplied project-id exists or project
   * contains no activity model or if package-/activity-name is supplied but there is no matching
   * package/activity.
   */
  GetActionsResult getActions(GetActionsRequest request)
      throws IllegalArgumentException, NoSuchElementException, ServiceException;

  /**
   * @throws NoSuchElementException If no project with supplied project-id exists or project
   * contains no activity model or if package-/activity-name is supplied but there is no matching
   * package/activity.
   */
  GetNodesResult getNodes(GetNodesRequest request)
      throws IllegalArgumentException, NoSuchElementException, ServiceException;

  // State-machine model specific

  /**
   * @throws NoSuchElementException If no project with supplied project-id exists or project
   * contains no state-machine model.
   */
  GetFunctionCallsResult getFunctionCalls(GetFunctionCallsRequest request)
      throws IllegalArgumentException, NoSuchElementException;
}
