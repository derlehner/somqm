package at.ac.tuwien.big.ame.somqm.server.service;

import at.ac.tuwien.big.ame.somqm.server.dto.query.EntityInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.RelationInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Action;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Activity;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Node;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.enumeration.NodeType;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassAttribute;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassOperation;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.FunctionCall;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelUml2Content;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

public interface Uml2ExtractorService {

  /**
   * @param sourceEntityNameFilter If null: Filter is ignored.
   * @param targetEntityNameFilter If null: Filter is ignored.
   */
  List<RelationInfo> extractAll(UmlModelUml2Content content, Pattern sourceEntityNameFilter,
      Pattern targetEntityNameFilter) throws IllegalArgumentException, ServiceException;

  /**
   * @param packageNameFilter If null: Filter is ignored.
   */
  List<Package> extractPackages(UmlModelUml2Content content, Pattern packageNameFilter)
      throws IllegalArgumentException;

  /**
   * @param packageNameFilter If null: Filter is ignored.
   * @param entityNameFilter If null: Filter is ignored.
   * @throws NoSuchElementException If package-name-filter is supplied but there is no matching
   * package.
   */
  List<EntityInfo> extractEntities(UmlModelUml2Content content, boolean includeTypeSpecificDetails,
      Pattern packageNameFilter, Pattern entityNameFilter)
      throws IllegalArgumentException, NoSuchElementException, ServiceException;

  /**
   * @param sourceEntityNameFilter If null: Filter is ignored.
   * @param targetEntityNameFilter If null: Filter is ignored.
   */
  List<RelationInfo> extractRelations(UmlModelUml2Content content,
      boolean includeTypeSpecificDetails, Pattern sourceEntityNameFilter,
      Pattern targetEntityNameFilter) throws IllegalArgumentException, ServiceException;

  /**
   * @param entityNameFilter If null: Filter is ignored.
   * @param attributeNameFilter If null: Filter is ignored.
   * @throws NoSuchElementException If entity-name-filter is supplied but there is no matching
   * entity.
   */
  List<ClassAttribute> extractAttributes(UmlModelUml2Content content, Pattern entityNameFilter,
      Pattern attributeNameFilter) throws IllegalArgumentException, NoSuchElementException;

  /**
   * @param entityNameFilter If null: Filter is ignored.
   * @param operationNameFilter If null: Filter is ignored.
   * @throws NoSuchElementException If entity-name-filter is supplied but there is no matching
   * entity.
   */
  List<ClassOperation> extractOperations(UmlModelUml2Content content, Pattern entityNameFilter,
      Pattern operationNameFilter) throws IllegalArgumentException, NoSuchElementException;

  /**
   * @param packageNameFilter If null: Filter is ignored.
   * @param activityNameFilter If null: Filter is ignored.
   * @throws NoSuchElementException If package-name-filter is supplied but there is no matching
   * package.
   */
  List<Activity> extractActivities(UmlModelUml2Content content, Pattern packageNameFilter,
      Pattern activityNameFilter) throws IllegalArgumentException, NoSuchElementException;

  /**
   * @param packageNameFilter If null: Filter is ignored.
   * @param activityNameFilter If null: Filter is ignored.
   * @param actionNameFilter If null: Filter is ignored.
   * @throws NoSuchElementException If package-name-filter is supplied but there is no matching
   * package or if activity-name-filter is supplied but there is no matching activity.
   */
  List<Action> extractActions(UmlModelUml2Content content, Pattern packageNameFilter,
      Pattern activityNameFilter, Pattern actionNameFilter)
      throws IllegalArgumentException, NoSuchElementException, ServiceException;

  /**
   * @param packageNameFilter If null: Filter is ignored.
   * @param activityNameFilter If null: Filter is ignored.
   * @param nodeNameFilter If null: Filter is ignored.
   * @param nodeTypeFilter If null: Filter is ignored.
   * @throws NoSuchElementException If package-name-filter is supplied but there is no matching
   * package or if activity-name-filter is supplied but there is no matching activity.
   */
  List<Node> extractNodes(UmlModelUml2Content content, Pattern packageNameFilter,
      Pattern activityNameFilter, Pattern nodeNameFilter, NodeType nodeTypeFilter)
      throws IllegalArgumentException, NoSuchElementException, ServiceException;

  /**
   * @param functionCallNameFilter If null: Filter is ignored.
   */
  List<FunctionCall> extractFunctionCalls(UmlModelUml2Content content,
      Pattern functionCallNameFilter) throws IllegalArgumentException;
}
