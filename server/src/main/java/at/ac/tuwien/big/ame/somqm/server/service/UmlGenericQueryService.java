package at.ac.tuwien.big.ame.somqm.server.service;

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
import java.util.NoSuchElementException;

public interface UmlGenericQueryService {

  /**
   * @throws NoSuchElementException If no model with supplied model-id exists.
   */
  GetAllResult getAll(GetAllRequest request)
      throws IllegalArgumentException, NoSuchElementException, ServiceException;

  /**
   * @throws NoSuchElementException If no model with supplied model-id exists.
   */
  GetPackagesResult getPackages(GetPackagesRequest request)
      throws IllegalArgumentException, NoSuchElementException;

  /**
   * @throws NoSuchElementException If no model with supplied model-id exists or if package-name is
   * supplied but there is no matching package.
   */
  GetEntitiesResult getEntities(GetEntitiesRequest request)
      throws IllegalArgumentException, NoSuchElementException, ServiceException;

  /**
   * @throws NoSuchElementException If no model with supplied model-id exists.
   */
  GetRelationsResult getRelations(GetRelationsRequest request)
      throws IllegalArgumentException, NoSuchElementException, ServiceException;

  /**
   * Only for activity and state-machine model-types.
   *
   * @return The first found initial state of the given model.
   * @throws IllegalArgumentException If found model is not of type state-machine or activity
   * model.
   * @throws NoSuchElementException If no model with supplied model-id exists or if found model has
   * no initial state.
   */
  GetInitialStateResult getInitialState(GetInitialStateRequest request)
      throws IllegalArgumentException, NoSuchElementException, ServiceException;

  /**
   * Only for activity and state-machine model-types.
   *
   * @return The first found final state of the given model.
   * @throws IllegalArgumentException If found model is not of type state-machine or activity
   * model.
   * @throws NoSuchElementException If no model with supplied model-id exists or if found model has
   * no final state.
   */
  GetFinalStateResult getFinalState(GetFinalStateRequest request)
      throws IllegalArgumentException, NoSuchElementException, ServiceException;
}
