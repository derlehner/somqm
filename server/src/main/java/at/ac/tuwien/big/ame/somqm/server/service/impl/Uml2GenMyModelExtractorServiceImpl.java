package at.ac.tuwien.big.ame.somqm.server.service.impl;

import at.ac.tuwien.big.ame.somqm.server.dto.query.EntityInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.RelationInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Modifier;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.ActivityModelRelationInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Node;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.enumeration.ActionType;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.enumeration.NodeType;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassAttribute;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassModelDefaultRelationInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassModelEntityInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassModelGeneralizationRelationInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassModelInterfaceRealizationRelationInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassOperation;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassOperationParameter;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.enumeration.ClassType;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.enumeration.VisibilityType;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.FunctionCall;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.StateMachineModelRelationInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.enumeration.PseudostateType;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelUml2Content;
import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;
import at.ac.tuwien.big.ame.somqm.server.service.ServiceException;
import at.ac.tuwien.big.ame.somqm.server.service.Uml2ExtractorService;
import com.google.common.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.BehavioredClassifier;
import org.eclipse.uml2.uml.CallBehaviorAction;
import org.eclipse.uml2.uml.CallOperationAction;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.ControlNode;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.FinalNode;
import org.eclipse.uml2.uml.FinalState;
import org.eclipse.uml2.uml.FlowFinalNode;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.InitialNode;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.JoinNode;
import org.eclipse.uml2.uml.MergeNode;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.PseudostateKind;
import org.eclipse.uml2.uml.Relationship;
import org.eclipse.uml2.uml.SendSignalAction;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StructuredActivityNode;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.Vertex;
import org.eclipse.uml2.uml.VisibilityKind;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for XMI-format of models created with https://www.genmymodel.com/
 */

@Service
public class Uml2GenMyModelExtractorServiceImpl implements Uml2ExtractorService {

  @Autowired
  public Uml2GenMyModelExtractorServiceImpl() {

  }

  @VisibleForTesting
  public static List<RelationInfo> extractRelationsInternal(UmlModelUml2Content content,
      boolean includeRelationTypeSpecificDetails, boolean includeEntityTypeSpecificDetails,
      Pattern sourceEntityNameFilter, Pattern targetEntityNameFilter)
      throws IllegalArgumentException, ServiceException {
    if (content == null) {
      throw new IllegalArgumentException("Supplied content is invalid");
    }

    List<RelationInfo> relationInfos = new ArrayList<>();
    for (Map.Entry<Package, at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package> package_ : Uml2Iterator
        .getPackages(content.getModel(), null).entrySet()) {
      if (content.getType() == UmlModelType.CLASS_MODEL) {
        for (Relationship relationship : Uml2Iterator
            .getElementsBelow(package_.getKey(), Relationship.class)) {
          Optional<RelationInfo> relationInfo;
          if (relationship instanceof Association) {
            Association association = (Association) relationship;
            relationInfo = DtoConverter
                .extractClassModelRelation(association, package_.getValue(), sourceEntityNameFilter,
                    targetEntityNameFilter, includeRelationTypeSpecificDetails,
                    includeEntityTypeSpecificDetails);
          } else if (relationship instanceof Generalization) {
            Generalization generalization = (Generalization) relationship;
            relationInfo = DtoConverter
                .extractClassModelRelation(generalization, package_.getValue(),
                    sourceEntityNameFilter, targetEntityNameFilter,
                    includeRelationTypeSpecificDetails, includeEntityTypeSpecificDetails);
          } else if (relationship instanceof InterfaceRealization) {
            InterfaceRealization interfaceRealization = (InterfaceRealization) relationship;
            relationInfo = DtoConverter
                .extractClassModelRelation(interfaceRealization, package_.getValue(),
                    sourceEntityNameFilter, targetEntityNameFilter,
                    includeRelationTypeSpecificDetails, includeEntityTypeSpecificDetails);
          } else if (relationship instanceof PackageImport) {
            // Ignore PackageImport elements
            relationInfo = Optional.empty();
          } else {
            throw new ServiceException(
                "No extraction implemented for class-model relationship of type '" + relationship
                    .getClass().getName() + "'");
          }
          relationInfo.ifPresent(relationInfos::add);
        }
      } else if (content.getType() == UmlModelType.ACTIVITY_MODEL) {
        for (ControlFlow controlFlow : Uml2Iterator
            .getElementsBelow(package_.getKey(), ControlFlow.class)) {
          Optional<RelationInfo> relationInfo = DtoConverter
              .extractActivityModelRelation(controlFlow, package_.getValue(),
                  sourceEntityNameFilter,
                  targetEntityNameFilter, includeRelationTypeSpecificDetails,
                  includeEntityTypeSpecificDetails);
          relationInfo.ifPresent(relationInfos::add);
        }
      } else if (content.getType() == UmlModelType.STATE_MACHINE_MODEL) {
        for (Transition transition : Uml2Iterator
            .getElementsBelow(package_.getKey(), Transition.class)) {
          Optional<RelationInfo> relationInfo = DtoConverter
              .extractStateMachineModelRelation(transition, package_.getValue(),
                  sourceEntityNameFilter,
                  targetEntityNameFilter, includeRelationTypeSpecificDetails,
                  includeEntityTypeSpecificDetails);
          relationInfo.ifPresent(relationInfos::add);
        }
      } else {
        throw new ServiceException("Supplied type '" + content.getType() + "' not implemented");
      }
    }
    return relationInfos;
  }

  @Override
  public List<RelationInfo> extractAll(UmlModelUml2Content content, Pattern sourceEntityNameFilter,
      Pattern targetEntityNameFilter) throws IllegalArgumentException, ServiceException {
    boolean includeRelationTypeSpecificDetails = true;
    boolean includeEntityTypeSpecificDetails = true;
    return extractRelationsInternal(content, includeRelationTypeSpecificDetails,
        includeEntityTypeSpecificDetails, sourceEntityNameFilter, targetEntityNameFilter);
  }

  @Override
  public List<at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package> extractPackages(
      UmlModelUml2Content content, Pattern packageNameFilter) throws IllegalArgumentException {
    if (content == null) {
      throw new IllegalArgumentException("Supplied content is invalid");
    }
    Map<Package, at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package> packages = Uml2Iterator
        .getPackages(content.getModel(), packageNameFilter);
    return new ArrayList<>(packages.values());
  }

  @Override
  public List<EntityInfo> extractEntities(UmlModelUml2Content content,
      boolean includeTypeSpecificDetails, Pattern packageNameFilter, Pattern entityNameFilter)
      throws IllegalArgumentException, NoSuchElementException, ServiceException {
    if (content == null) {
      throw new IllegalArgumentException("Supplied content is invalid");
    }

    List<EntityInfo> result = new ArrayList<>();
    boolean matchingPackageFound = false;

    for (Map.Entry<Package, at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package> package_ : Uml2Iterator
        .getPackages(content.getModel(), packageNameFilter).entrySet()) {
      matchingPackageFound = true;
      if (content.getType() == UmlModelType.CLASS_MODEL) {
        for (Class class_ : Uml2Iterator.getElementsBelow(package_.getKey(), Class.class)) {
          EntityInfo classEntity = DtoConverter
              .convert(class_, package_.getValue(), includeTypeSpecificDetails);
          if (Matcher.isNameMatching(classEntity, entityNameFilter)) {
            result.add(classEntity);
          }
        }
        for (Interface interface_ : Uml2Iterator
            .getElementsBelow(package_.getKey(), Interface.class)) {
          EntityInfo interfaceEntity = DtoConverter
              .convert(interface_, package_.getValue(), includeTypeSpecificDetails);
          if (Matcher.isNameMatching(interfaceEntity, entityNameFilter)) {
            result.add(interfaceEntity);
          }
        }
      } else if (content.getType() == UmlModelType.ACTIVITY_MODEL) {
        for (Activity activity : Uml2Iterator.getElementsBelow(package_.getKey(), Activity.class)) {
          EntityInfo activityEntity = DtoConverter
              .convert(activity, package_.getValue(), includeTypeSpecificDetails);
          if (Matcher.isNameMatching(activityEntity, entityNameFilter)) {
            result.add(activityEntity);
          }
        }
        for (Action action : Uml2Iterator.getElementsBelow(package_.getKey(), Action.class)) {
          EntityInfo actionEntity = DtoConverter
              .convert(action, package_.getValue(), includeTypeSpecificDetails);
          if (Matcher.isNameMatching(actionEntity, entityNameFilter)) {
            result.add(actionEntity);
          }
        }
        for (ControlNode controlNode : Uml2Iterator
            .getElementsBelow(package_.getKey(), ControlNode.class)) {
          EntityInfo controlNodeEntity = DtoConverter
              .convert(controlNode, package_.getValue(), includeTypeSpecificDetails);
          if (Matcher.isNameMatching(controlNodeEntity, entityNameFilter)) {
            result.add(controlNodeEntity);
          }
        }
      } else if (content.getType() == UmlModelType.STATE_MACHINE_MODEL) {
        for (State state : Uml2Iterator.getElementsBelow(package_.getKey(), State.class)) {
          EntityInfo stateEntity = DtoConverter
              .convert(state, package_.getValue(), includeTypeSpecificDetails);
          if (Matcher.isNameMatching(stateEntity, entityNameFilter)) {
            result.add(stateEntity);
          }
        }
        for (Pseudostate pseudostate : Uml2Iterator
            .getElementsBelow(package_.getKey(), Pseudostate.class)) {
          EntityInfo pseudostateEntity = DtoConverter
              .convert(pseudostate, package_.getValue(), includeTypeSpecificDetails);
          if (Matcher.isNameMatching(pseudostateEntity, entityNameFilter)) {
            result.add(pseudostateEntity);
          }
        }
      } else {
        throw new ServiceException("Supplied type '" + content.getType() + "' not implemented");
      }
    }

    if (packageNameFilter != null && !matchingPackageFound) {
      throw new NoSuchElementException("No package matches supplied package-name-filter");
    }

    return result;
  }

  @Override
  public List<RelationInfo> extractRelations(UmlModelUml2Content content,
      boolean includeTypeSpecificDetails, Pattern sourceEntityNameFilter,
      Pattern targetEntityNameFilter) throws IllegalArgumentException, ServiceException {
    boolean includeRelationTypeSpecificDetails = includeTypeSpecificDetails;
    boolean includeEntityTypeSpecificDetails = false;
    return extractRelationsInternal(content, includeRelationTypeSpecificDetails,
        includeEntityTypeSpecificDetails, sourceEntityNameFilter, targetEntityNameFilter);
  }

  @Override
  public List<ClassAttribute> extractAttributes(UmlModelUml2Content content,
      Pattern entityNameFilter, Pattern attributeNameFilter)
      throws IllegalArgumentException, NoSuchElementException {
    if (content == null) {
      throw new IllegalArgumentException("Supplied content is invalid");
    }

    List<ClassAttribute> result = new ArrayList<>();
    boolean matchingEntityFound = false;

    for (Map.Entry<Package, at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package> package_ : Uml2Iterator
        .getPackages(content.getModel(), null).entrySet()) {
      for (Class class_ : Uml2Iterator.getElementsBelow(package_.getKey(), Class.class)) {
        EntityInfo classEntity = DtoConverter.convert(class_, package_.getValue(), false);
        if (Matcher.isNameMatching(classEntity, entityNameFilter)) {
          matchingEntityFound = true;
          for (Property attribute : class_.getAllAttributes()) {
            DtoConverter.extractAttribute(attribute, package_.getValue(), attributeNameFilter)
                .ifPresent(result::add);
          }
        }
      }
      for (Interface interface_ : Uml2Iterator
          .getElementsBelow(package_.getKey(), Interface.class)) {
        EntityInfo interfaceEntity = DtoConverter.convert(interface_, package_.getValue(), false);
        if (Matcher.isNameMatching(interfaceEntity, entityNameFilter)) {
          matchingEntityFound = true;
          for (Property attribute : interface_.getAllAttributes()) {
            DtoConverter.extractAttribute(attribute, package_.getValue(), attributeNameFilter)
                .ifPresent(result::add);
          }
        }
      }
    }

    if (entityNameFilter != null && !matchingEntityFound) {
      throw new NoSuchElementException("No entity matches supplied entity-name-filter");
    }

    return result;
  }

  @Override
  public List<ClassOperation> extractOperations(UmlModelUml2Content content,
      Pattern entityNameFilter, Pattern operationNameFilter)
      throws IllegalArgumentException, NoSuchElementException {
    if (content == null) {
      throw new IllegalArgumentException("Supplied content is invalid");
    }

    List<ClassOperation> result = new ArrayList<>();
    boolean matchingEntityFound = false;

    for (Map.Entry<Package, at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package> package_ : Uml2Iterator
        .getPackages(content.getModel(), null).entrySet()) {
      for (Class class_ : Uml2Iterator.getElementsBelow(package_.getKey(), Class.class)) {
        EntityInfo classEntity = DtoConverter.convert(class_, package_.getValue(), false);
        if (Matcher.isNameMatching(classEntity, entityNameFilter)) {
          matchingEntityFound = true;
          for (Operation operation : class_.getAllOperations()) {
            DtoConverter.extractOperation(operation, package_.getValue(), operationNameFilter)
                .ifPresent(result::add);
          }
        }
      }
      for (Interface interface_ : Uml2Iterator
          .getElementsBelow(package_.getKey(), Interface.class)) {
        EntityInfo interfaceEntity = DtoConverter.convert(interface_, package_.getValue(), false);
        if (Matcher.isNameMatching(interfaceEntity, entityNameFilter)) {
          matchingEntityFound = true;
          for (Operation operation : interface_.getAllOperations()) {
            DtoConverter.extractOperation(operation, package_.getValue(), operationNameFilter)
                .ifPresent(result::add);
          }
        }
      }
    }

    if (entityNameFilter != null && !matchingEntityFound) {
      throw new NoSuchElementException("No entity matches supplied entity-name-filter");
    }

    return result;
  }

  @Override
  public List<at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Activity> extractActivities(
      UmlModelUml2Content content, Pattern packageNameFilter, Pattern activityNameFilter)
      throws IllegalArgumentException, NoSuchElementException {
    if (content == null) {
      throw new IllegalArgumentException("Supplied content is invalid");
    }

    List<at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Activity> result = new ArrayList<>();
    boolean matchingPackageFound = false;

    for (Map.Entry<Package, at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package> package_ : Uml2Iterator
        .getPackages(content.getModel(), packageNameFilter).entrySet()) {
      matchingPackageFound = true;
      for (Activity activity : Uml2Iterator.getElementsBelow(package_.getKey(), Activity.class)) {
        EntityInfo activityEntity = DtoConverter.convert(activity, package_.getValue(), true);
        if (Matcher.isNameMatching(activityEntity, activityNameFilter)) {
          result.add(
              (at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Activity) activityEntity);
        }
      }
    }

    if (packageNameFilter != null && !matchingPackageFound) {
      throw new NoSuchElementException("No package matches supplied package-name-filter");
    }

    return result;
  }

  @Override
  public List<at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Action> extractActions(
      UmlModelUml2Content content, Pattern packageNameFilter, Pattern activityNameFilter,
      Pattern actionNameFilter)
      throws IllegalArgumentException, NoSuchElementException, ServiceException {
    if (content == null) {
      throw new IllegalArgumentException("Supplied content is invalid");
    }

    List<at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Action> result = new ArrayList<>();
    boolean matchingPackageFound = false;
    boolean matchingActivityFound = false;

    for (Map.Entry<Package, at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package> package_ : Uml2Iterator
        .getPackages(content.getModel(), packageNameFilter).entrySet()) {
      matchingPackageFound = true;
      for (Activity activity : Uml2Iterator.getElementsBelow(package_.getKey(), Activity.class)) {
        EntityInfo activityEntity = DtoConverter.convert(activity, package_.getValue(), false);
        if (Matcher.isNameMatching(activityEntity, activityNameFilter)) {
          matchingActivityFound = true;
          for (Action action : Uml2Iterator.getElementsBelow(activity, Action.class)) {
            EntityInfo actionEntity = DtoConverter.convert(action, package_.getValue(), true);
            if (Matcher.isNameMatching(actionEntity, actionNameFilter)) {
              result.add(
                  (at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Action) actionEntity);
            }
          }
        }
      }
    }

    if (packageNameFilter != null && !matchingPackageFound) {
      throw new NoSuchElementException("No package matches supplied package-name-filter");
    }
    if (activityNameFilter != null && !matchingActivityFound) {
      throw new NoSuchElementException("No activity matches supplied activity-name-filter");
    }

    return result;
  }

  @Override
  public List<Node> extractNodes(UmlModelUml2Content content, Pattern packageNameFilter,
      Pattern activityNameFilter, Pattern nodeNameFilter, NodeType nodeTypeFilter)
      throws IllegalArgumentException, NoSuchElementException, ServiceException {
    if (content == null) {
      throw new IllegalArgumentException("Supplied content is invalid");
    }

    List<Node> result = new ArrayList<>();
    boolean matchingPackageFound = false;
    boolean matchingActivityFound = false;

    for (Map.Entry<Package, at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package> package_ : Uml2Iterator
        .getPackages(content.getModel(), packageNameFilter).entrySet()) {
      matchingPackageFound = true;
      for (Activity activity : Uml2Iterator.getElementsBelow(package_.getKey(), Activity.class)) {
        EntityInfo activityEntity = DtoConverter.convert(activity, package_.getValue(), false);
        if (Matcher.isNameMatching(activityEntity, activityNameFilter)) {
          matchingActivityFound = true;
          for (ControlNode controlNode : Uml2Iterator
              .getElementsBelow(activity, ControlNode.class)) {
            EntityInfo controlNodeEntityRaw = DtoConverter
                .convert(controlNode, package_.getValue(), true);
            if (Matcher.isNameMatching(controlNodeEntityRaw, nodeNameFilter)) {
              Node nodeEntity = (Node) controlNodeEntityRaw;
              if (nodeTypeFilter == null || nodeEntity.getType() == nodeTypeFilter) {
                result.add(nodeEntity);
              }
            }
          }
        }
      }
    }

    if (packageNameFilter != null && !matchingPackageFound) {
      throw new NoSuchElementException("No package matches supplied package-name-filter");
    }
    if (activityNameFilter != null && !matchingActivityFound) {
      throw new NoSuchElementException("No activity matches supplied activity-name-filter");
    }

    return result;
  }

  @Override
  public List<FunctionCall> extractFunctionCalls(UmlModelUml2Content content,
      Pattern functionCallNameFilter) throws IllegalArgumentException {
    if (content == null) {
      throw new IllegalArgumentException("Supplied content is invalid");
    }

    List<FunctionCall> result = new ArrayList<>();
    for (Map.Entry<Package, at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package> package_ : Uml2Iterator
        .getPackages(content.getModel(), null).entrySet()) {
      for (State state : Uml2Iterator.getElementsBelow(package_.getKey(), State.class)) {
        DtoConverter.extractFunctionCall(state.getDoActivity(), package_.getValue(),
            functionCallNameFilter).ifPresent(result::add);
        DtoConverter
            .extractFunctionCall(state.getEntry(), package_.getValue(), functionCallNameFilter)
            .ifPresent(result::add);
        DtoConverter
            .extractFunctionCall(state.getExit(), package_.getValue(), functionCallNameFilter)
            .ifPresent(result::add);
      }
      for (Transition transition : Uml2Iterator
          .getElementsBelow(package_.getKey(), Transition.class)) {
        DtoConverter.extractFunctionCall(transition.getEffect(), package_.getValue(),
            functionCallNameFilter).ifPresent(result::add);
      }
    }

    return result;
  }

  private static class Uml2Iterator {

    /*
     * @param includeSubTypes E.g. if Literals.STATE is passed as classType and includeSubTypes is
     * set to true, then not only State but also FinalState instances get returned.
    static Set<Element> getAllElementsBelow(EObject root, EClass classType, boolean includeSubTypes)
        throws IllegalArgumentException {
      if (root == null || classType == null) {
        throw new IllegalArgumentException();
      }
      Set<Element> result = new HashSet<>();
      for (Iterator allContents = UMLUtil.getAllContents(root, true, false);
          allContents.hasNext(); ) {
        EObject eObject = (EObject) allContents.next();
        if (eObject instanceof Element && (includeSubTypes ? classType.isInstance(eObject)
            : eObject.eClass().equals(classType))) {
          result.add((Element) eObject);
        }
      }
      return result;
    }
    */

    /**
     * @param packageNameFilter If null: Filter is ignored.
     */
    static Map<Package, at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package> getPackages(
        Model model, Pattern packageNameFilter) throws IllegalArgumentException {
      if (model == null) {
        throw new IllegalArgumentException();
      }
      return getPackagesInternal(model, packageNameFilter, null, new HashMap<>());
    }

    /**
     * Nested packages don't get iterated.
     *
     * @param classType E.g. if State.class is passed, then not only State but also FinalState
     * instances get returned.
     */
    static <T extends Element> Set<T> getElementsBelow(Element element,
        java.lang.Class<T> classType) throws IllegalArgumentException {
      if (element == null || classType == null) {
        throw new IllegalArgumentException();
      }
      return getElementsBelowInternal(element, classType, new HashSet<>());
    }

    private static Map<Package, at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package> getPackagesInternal(
        Package currentPackage, Pattern packageNameFilter,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package currentPackage_,
        Map<Package, at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package> results) {
      if (currentPackage == null || results == null) {
        throw new IllegalArgumentException();
      }
      for (Package nestedPackage : currentPackage.getNestedPackages()) {
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package nestedPackage_ = new at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package(
            nestedPackage.getName(), currentPackage_);
        getPackagesInternal(nestedPackage, packageNameFilter, nestedPackage_, results);
      }
      if (currentPackage_ == null) {
        currentPackage_ = new at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package(
            "", null);
      }
      if (Matcher.isNameMatching(currentPackage_, packageNameFilter)) {
        results.put(currentPackage, currentPackage_);
      }
      return results;
    }

    private static <T extends Element> Set<T> getElementsBelowInternal(EObject currentEObject,
        java.lang.Class<T> classType, Set<T> results) throws IllegalArgumentException {
      if (currentEObject == null || classType == null || results == null) {
        throw new IllegalArgumentException();
      }
      for (EObject eObject : currentEObject.eContents()) {
        if (!(eObject instanceof Package)) { // Don't iterate over nested packages.
          if (classType.isInstance(eObject)) {
            T element = classType.cast(eObject);
            results.add(element);
          }
          getElementsBelowInternal(eObject, classType, results);
        }
      }
      return results;
    }
  }

  private static class Matcher {

    static boolean isNameMatching(EntityInfo entityInfo, Pattern entityNameFilter)
        throws IllegalArgumentException {
      if (entityInfo == null) {
        throw new IllegalArgumentException();
      }
      return entityNameFilter == null || entityNameFilter.matcher(entityInfo.getName()).matches();
    }

    static boolean isNameMatching(
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        Pattern packageNameFilter) throws IllegalArgumentException {
      if (package_ == null) {
        throw new IllegalArgumentException();
      }
      return packageNameFilter == null || packageNameFilter.matcher(package_.getFullName())
          .matches();
    }

    static boolean isNameMatching(FunctionCall functionCall, Pattern functionCallNameFilter)
        throws IllegalArgumentException {
      if (functionCall == null) {
        throw new IllegalArgumentException();
      }
      return functionCallNameFilter == null || functionCallNameFilter
          .matcher(functionCall.getName()).matches();
    }

    static boolean isNameMatching(ClassAttribute classAttribute, Pattern classAttributeNameFilter)
        throws IllegalArgumentException {
      if (classAttribute == null) {
        throw new IllegalArgumentException();
      }
      return classAttributeNameFilter == null || classAttributeNameFilter
          .matcher(classAttribute.getName()).matches();
    }

    static boolean isNameMatching(ClassOperation classOperation, Pattern classOperationNameFilter)
        throws IllegalArgumentException {
      if (classOperation == null) {
        throw new IllegalArgumentException();
      }
      return classOperationNameFilter == null || classOperationNameFilter
          .matcher(classOperation.getName()).matches();
    }
  }

  private static class DtoConverter {

    static Optional<FunctionCall> extractFunctionCall(Behavior behavior,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        Pattern nameFilter) throws IllegalArgumentException {
      if (package_ == null) {
        throw new IllegalArgumentException();
      }
      if (behavior == null) {
        return Optional.empty();
      }
      FunctionCall functionCall = DtoConverter.convert(behavior, package_);
      if (Matcher.isNameMatching(functionCall, nameFilter)) {
        return Optional.of(functionCall);
      }
      return Optional.empty();
    }

    static Optional<ClassAttribute> extractAttribute(Property attribute,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        Pattern nameFilter) throws IllegalArgumentException {
      if (attribute == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      ClassAttribute classAttribute = DtoConverter.convert(attribute, package_);
      if (Matcher.isNameMatching(classAttribute, nameFilter)) {
        return Optional.of(classAttribute);
      }
      return Optional.empty();
    }

    static Optional<ClassOperation> extractOperation(Operation operation,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        Pattern nameFilter) throws IllegalArgumentException {
      if (operation == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      ClassOperation classOperation = DtoConverter.convert(operation, package_);
      if (Matcher.isNameMatching(classOperation, nameFilter)) {
        return Optional.of(classOperation);
      }
      return Optional.empty();
    }

    static Optional<RelationInfo> extractClassModelRelation(Association association,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        Pattern sourceEntityNameFilter, Pattern targetEntityNameFilter,
        boolean includeRelationTypeSpecificDetails, boolean includeEntityTypeSpecificDetails)
        throws IllegalArgumentException, ServiceException {
      if (association == null || package_ == null) {
        throw new IllegalArgumentException();
      }

      List<Property> ownedEnds = association.getOwnedEnds();
      if (ownedEnds.size() != 2) {
        throw new ServiceException(
            "No extraction implemented for class-model relationship of type 'Association' where number of owned-ends is not exactly 2");
      }

      Property source = ownedEnds.get(0);
      EntityInfo sourceEntity = extractClassModelAssociationEntity(source, package_,
          includeEntityTypeSpecificDetails);
      if (!Matcher.isNameMatching(sourceEntity, sourceEntityNameFilter)) {
        return Optional.empty();
      }

      Property target = ownedEnds.get(1);
      EntityInfo targetEntity = extractClassModelAssociationEntity(target, package_,
          includeEntityTypeSpecificDetails);
      if (!Matcher.isNameMatching(targetEntity, targetEntityNameFilter)) {
        return Optional.empty();
      }

      RelationInfo relationInfo;
      if (includeRelationTypeSpecificDetails) {
        String name = association.getName();
        String sourceMultiplicity = extractMultiplicities(source, package_);
        boolean isSourceNavigable = source.isNavigable();
        String targetMultiplicity = extractMultiplicities(target, package_);
        boolean isTargetNavigable = target.isNavigable();
        relationInfo = new ClassModelDefaultRelationInfo(sourceEntity, targetEntity, name,
            sourceMultiplicity, isSourceNavigable, targetMultiplicity, isTargetNavigable);
      } else {
        relationInfo = new RelationInfo(sourceEntity, targetEntity);
      }
      return Optional.of(relationInfo);
    }

    static Optional<RelationInfo> extractClassModelRelation(Generalization generalization,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        Pattern sourceEntityNameFilter, Pattern targetEntityNameFilter,
        boolean includeRelationTypeSpecificDetails, boolean includeEntityTypeSpecificDetails)
        throws IllegalArgumentException, ServiceException {
      if (generalization == null || package_ == null) {
        throw new IllegalArgumentException();
      }

      Classifier source = generalization.getSpecific();
      EntityInfo sourceEntity = extractClassModelGeneralizationEntity(source, package_,
          includeEntityTypeSpecificDetails);
      if (!Matcher.isNameMatching(sourceEntity, sourceEntityNameFilter)) {
        return Optional.empty();
      }

      Classifier target = generalization.getGeneral();
      EntityInfo targetEntity = extractClassModelGeneralizationEntity(target, package_,
          includeEntityTypeSpecificDetails);
      if (!Matcher.isNameMatching(targetEntity, targetEntityNameFilter)) {
        return Optional.empty();
      }

      RelationInfo relationInfo;
      if (includeRelationTypeSpecificDetails) {
        relationInfo = new ClassModelGeneralizationRelationInfo(sourceEntity, targetEntity);
      } else {
        relationInfo = new RelationInfo(sourceEntity, targetEntity);
      }
      return Optional.of(relationInfo);
    }

    static Optional<RelationInfo> extractClassModelRelation(
        InterfaceRealization interfaceRealization,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        Pattern sourceEntityNameFilter, Pattern targetEntityNameFilter,
        boolean includeRelationTypeSpecificDetails, boolean includeEntityTypeSpecificDetails)
        throws IllegalArgumentException, ServiceException {
      if (interfaceRealization == null || package_ == null) {
        throw new IllegalArgumentException();
      }

      BehavioredClassifier source = interfaceRealization.getImplementingClassifier();
      if (!(source instanceof Class)) {
        throw new ServiceException(
            "No extraction implemented for class-model relationship of type 'InterfaceRealization' where source (= implementing classifier) is not of type 'Class'");
      }
      EntityInfo sourceEntity = DtoConverter
          .convert((Class) source, package_, includeEntityTypeSpecificDetails);
      if (!Matcher.isNameMatching(sourceEntity, sourceEntityNameFilter)) {
        return Optional.empty();
      }

      Interface target = interfaceRealization.getContract();
      EntityInfo targetEntity = DtoConverter
          .convert(target, package_, includeEntityTypeSpecificDetails);
      if (!Matcher.isNameMatching(targetEntity, targetEntityNameFilter)) {
        return Optional.empty();
      }

      RelationInfo relationInfo;
      if (includeRelationTypeSpecificDetails) {
        relationInfo = new ClassModelInterfaceRealizationRelationInfo(sourceEntity, targetEntity);
      } else {
        relationInfo = new RelationInfo(sourceEntity, targetEntity);
      }
      return Optional.of(relationInfo);
    }

    static Optional<RelationInfo> extractActivityModelRelation(ControlFlow controlFlow,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        Pattern sourceEntityNameFilter, Pattern targetEntityNameFilter,
        boolean includeRelationTypeSpecificDetails, boolean includeEntityTypeSpecificDetails)
        throws IllegalArgumentException, ServiceException {
      if (controlFlow == null || package_ == null) {
        throw new IllegalArgumentException();
      }

      ActivityNode source = controlFlow.getSource();
      EntityInfo sourceEntity = convert(source, package_, includeEntityTypeSpecificDetails);
      if (!Matcher.isNameMatching(sourceEntity, sourceEntityNameFilter)) {
        return Optional.empty();
      }

      ActivityNode target = controlFlow.getTarget();
      EntityInfo targetEntity = convert(target, package_, includeEntityTypeSpecificDetails);
      if (!Matcher.isNameMatching(targetEntity, targetEntityNameFilter)) {
        return Optional.empty();
      }

      RelationInfo relationInfo;
      if (includeRelationTypeSpecificDetails) {
        String guard = controlFlow.getGuard() == null ? null : controlFlow.getGuard().stringValue();
        relationInfo = new ActivityModelRelationInfo(sourceEntity, targetEntity, guard);
      } else {
        relationInfo = new RelationInfo(sourceEntity, targetEntity);
      }
      return Optional.of(relationInfo);
    }

    static Optional<RelationInfo> extractStateMachineModelRelation(Transition transition,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        Pattern sourceEntityNameFilter, Pattern targetEntityNameFilter,
        boolean includeRelationTypeSpecificDetails, boolean includeEntityTypeSpecificDetails)
        throws IllegalArgumentException, ServiceException {
      if (transition == null || package_ == null) {
        throw new IllegalArgumentException();
      }

      Vertex source = transition.getSource();
      EntityInfo sourceEntity = convert(source, package_, includeEntityTypeSpecificDetails);
      if (!Matcher.isNameMatching(sourceEntity, sourceEntityNameFilter)) {
        return Optional.empty();
      }

      Vertex target = transition.getTarget();
      EntityInfo targetEntity = convert(target, package_, includeEntityTypeSpecificDetails);
      if (!Matcher.isNameMatching(targetEntity, targetEntityNameFilter)) {
        return Optional.empty();
      }

      RelationInfo relationInfo;
      if (includeRelationTypeSpecificDetails) {
        List<String> triggers = new ArrayList<>();
        for (Trigger trigger : transition.getTriggers()) {
          triggers.add(trigger.getName());
        }
        String guard = transition.getGuard() == null ? null
            : transition.getGuard().getSpecification().stringValue();
        FunctionCall effect = extractFunctionCall(transition.getEffect(), package_, null)
            .orElse(null);
        relationInfo = new StateMachineModelRelationInfo(sourceEntity, targetEntity, triggers,
            guard, effect);
      } else {
        relationInfo = new RelationInfo(sourceEntity, targetEntity);
      }
      return Optional.of(relationInfo);
    }

    static String extractMultiplicities(Property ownedEnd,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_)
        throws IllegalArgumentException {
      if (ownedEnd == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      String lowerValue =
          ownedEnd.getLowerValue().isNull() ? "" : ownedEnd.getLowerValue().stringValue();
      String upperValue =
          ownedEnd.getUpperValue().isNull() ? "" : ownedEnd.getUpperValue().stringValue();
      return lowerValue + ".." + upperValue;
    }

    static EntityInfo extractClassModelAssociationEntity(Property associationEnd,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        boolean includeTypeSpecificDetails) throws IllegalArgumentException, ServiceException {
      if (associationEnd == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      Type associationEndType = associationEnd.getType();
      if (associationEndType instanceof Class) {
        return convert((Class) associationEndType, package_, includeTypeSpecificDetails);
      } else if (associationEndType instanceof Interface) {
        return convert((Interface) associationEndType, package_, includeTypeSpecificDetails);
      }
      throw new ServiceException(
          "No extraction implemented for class-model association end type '" + associationEndType
              .getClass().getName() + "'");
    }

    static EntityInfo extractClassModelGeneralizationEntity(Classifier generalizationEnd,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        boolean includeTypeSpecificDetails) throws IllegalArgumentException, ServiceException {
      if (generalizationEnd == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      if (generalizationEnd instanceof Class) {
        return convert((Class) generalizationEnd, package_, includeTypeSpecificDetails);
      } else if (generalizationEnd instanceof Interface) {
        return convert((Interface) generalizationEnd, package_, includeTypeSpecificDetails);
      }
      throw new ServiceException(
          "No extraction implemented for class-model generalization end type '" + generalizationEnd
              .getClass().getName() + "'");
    }

    static ClassModelEntityInfo extractAttributeType(Type type,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_)
        throws IllegalArgumentException {
      if (type == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      String name = type.getName();
      ClassType type_ = ClassType.DEFAULT;
      List<ClassAttribute> attributes = new ArrayList<>();
      List<ClassOperation> operations = new ArrayList<>();
      List<Modifier> modifiers = new ArrayList<>(); // TODO: Future work: Derive modifiers
      VisibilityType visibility = VisibilityType.PUBLIC;
      return new ClassModelEntityInfo(name, package_, type_, attributes, operations, modifiers,
          visibility);
    }

    static ClassModelEntityInfo extractOperationParameterType(Type type,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_)
        throws IllegalArgumentException {
      if (type == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      String name = type.getName();
      ClassType type_ = ClassType.DEFAULT;
      List<ClassAttribute> attributes = new ArrayList<>();
      List<ClassOperation> operations = new ArrayList<>();
      List<Modifier> modifiers = new ArrayList<>(); // TODO: Future work: Derive modifiers
      VisibilityType visibility = VisibilityType.PUBLIC;
      return new ClassModelEntityInfo(name, package_, type_, attributes, operations, modifiers,
          visibility);
    }

    static Optional<EntityInfo> extractOperationReturnType(Operation operation,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_)
        throws IllegalArgumentException {
      if (operation == null || package_ == null) {
        throw new IllegalArgumentException();
      }

      Parameter operationReturnParameter = operation.getReturnResult();
      if (operationReturnParameter == null) {
        return Optional.empty();
      }

      Type operationReturnParameterType = operationReturnParameter.getType();
      if (operationReturnParameterType == null) {
        return Optional.empty();
      }

      String name = operationReturnParameterType.getName();
      if (name == null) {
        /*
        Sometimes GenMyModel uses a genmymodel:// object to set the return type to 'void', e.g.:

        <ownedParameter ... direction="return">
          ...
          <type xsi:type="uml:DataType" href="genmymodel://_0sMAEHu2EemZdOOrcag7Wg#//void.1"/>
        </ownedParameter>
        */
        if (operationReturnParameterType instanceof DataType && operationReturnParameterType
            .eIsProxy()) {
          InternalEObject internalEObject = (InternalEObject) operationReturnParameterType;
          URI proxyURI = internalEObject.eProxyURI();
          if (proxyURI != null && proxyURI.toString().contains("genmymodel://") && proxyURI
              .toString().contains("void")) {
            name = "void";
          }
        }
        if (name == null) {
          return Optional.empty();
        }
      }

      return Optional.of(new EntityInfo(name, package_));
    }

    static EntityInfo convert(Action action,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        boolean includeTypeSpecificDetails) throws IllegalArgumentException, ServiceException {
      if (action == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      EntityInfo entityInfo;
      String name = action.getName();
      if (includeTypeSpecificDetails) {
        ActionType type = getActionType(action, package_);
        entityInfo = new at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Action(
            name, package_, type);
      } else {
        entityInfo = new EntityInfo(name, package_);
      }
      return entityInfo;
    }

    static FunctionCall convert(Behavior behavior,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_)
        throws IllegalArgumentException {
      if (behavior == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      String name = behavior.getName();
      return new FunctionCall(name);
    }

    static ActionType getActionType(Action action,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_)
        throws IllegalArgumentException, ServiceException {
      if (action == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      if (action instanceof OpaqueAction) {
        return ActionType.OPAQUE_ACTION;
      }
      if (action instanceof CallBehaviorAction) {
        return ActionType.CALL_BEHAVIOR;
      }
      if (action instanceof CallOperationAction) {
        return ActionType.CALL_OPERATION;
      }
      if (action instanceof StructuredActivityNode) {
        return ActionType.STRUCTURED_ACTIVITY;
      }
      if (action instanceof AcceptEventAction) {
        return ActionType.ACCEPT_EVENT;
      }
      if (action instanceof SendSignalAction) {
        return ActionType.SEND_SIGNAL;
      }
      throw new ServiceException(
          "No conversion implemented for action type '" + action.getClass().getName() + "'");
    }

    static NodeType getNodeType(ControlNode controlNode,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_)
        throws IllegalArgumentException, ServiceException {
      if (controlNode == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      if (controlNode instanceof InitialNode) {
        return NodeType.INITIAL;
      }
      if (controlNode instanceof ActivityFinalNode) {
        return NodeType.ACTIVITY_FINAL;
      }
      if (controlNode instanceof FlowFinalNode) {
        return NodeType.FLOW_FINAL;
      }
      if (controlNode instanceof FinalNode) {
        return NodeType.FINAL;
      }
      if (controlNode instanceof DecisionNode) {
        return NodeType.DECISION_SPLIT;
      }
      if (controlNode instanceof MergeNode) {
        return NodeType.DECISION_MERGE;
      }
      if (controlNode instanceof ForkNode) {
        return NodeType.FORK;
      }
      if (controlNode instanceof JoinNode) {
        return NodeType.SYNCHRONIZATION;
      }
      throw new ServiceException(
          "No conversion implemented for control-node type '" + controlNode.getClass().getName()
              + "'");
    }

    static ClassAttribute convert(Property attribute,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_)
        throws IllegalArgumentException {
      if (attribute == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      String name = attribute.getName();
      ClassModelEntityInfo type = null;
      if (attribute.getType() != null) {
        type = extractAttributeType(attribute.getType(), package_);
      }
      VisibilityType visibility = convert(attribute.getVisibility(), package_);
      List<Modifier> modifiers = new ArrayList<>(); // TODO: Future work: Modifiers can't be loaded from GenMyModel
      return new ClassAttribute(name, type, visibility, modifiers);
    }

    static EntityInfo convert(Class class_,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        boolean includeTypeSpecificDetails) throws IllegalArgumentException {
      if (class_ == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      String name = class_.getName();
      EntityInfo entityInfo;
      if (includeTypeSpecificDetails) {
        ClassType type = class_.isAbstract() ? ClassType.ABSTRACT : ClassType.DEFAULT;
        List<ClassAttribute> attributes = new ArrayList<>();
        for (Property attribute : class_.getAllAttributes()) {
          ClassAttribute classAttribute = convert(attribute, package_);
          attributes.add(classAttribute);
        }
        List<ClassOperation> operations = new ArrayList<>();
        for (Operation operation : class_.getOperations()) {
          ClassOperation classOperation = convert(operation, package_);
          operations.add(classOperation);
        }
        List<Modifier> modifiers = new ArrayList<>(); // TODO: Future work: Derive modifiers
        VisibilityType visibility = convert(class_.getVisibility(), package_);
        entityInfo = new ClassModelEntityInfo(name, package_, type, attributes, operations,
            modifiers, visibility);
      } else {
        entityInfo = new EntityInfo(name, package_);
      }
      return entityInfo;
    }

    static EntityInfo convert(Activity activity,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        boolean includeTypeSpecificDetails) throws IllegalArgumentException {
      if (activity == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      String name = activity.getName();
      EntityInfo entityInfo;
      if (includeTypeSpecificDetails) {
        entityInfo = new at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Activity(
            name, package_);
      } else {
        entityInfo = new EntityInfo(name, package_);
      }
      return entityInfo;
    }

    static EntityInfo convert(Interface interface_,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        boolean includeTypeSpecificDetails) throws IllegalArgumentException {
      if (interface_ == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      String name = interface_.getName();
      EntityInfo entityInfo;
      if (includeTypeSpecificDetails) {
        ClassType type = ClassType.INTERFACE;
        List<ClassAttribute> attributes = new ArrayList<>();
        for (Property attribute : interface_.getAllAttributes()) {
          ClassAttribute classAttribute = convert(attribute, package_);
          attributes.add(classAttribute);
        }
        List<ClassOperation> operations = new ArrayList<>();
        for (Operation operation : interface_.getOperations()) {
          ClassOperation classOperation = convert(operation, package_);
          operations.add(classOperation);
        }
        List<Modifier> modifiers = new ArrayList<>(); // TODO: Future work: Derive modifiers
        VisibilityType visibility = convert(interface_.getVisibility(), package_);
        entityInfo = new ClassModelEntityInfo(name, package_, type, attributes, operations,
            modifiers, visibility);
      } else {
        entityInfo = new EntityInfo(name, package_);
      }
      return entityInfo;
    }

    static ClassOperation convert(Operation operation,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_)
        throws IllegalArgumentException {
      if (operation == null || package_ == null) {
        throw new IllegalArgumentException();
      }

      String name = operation.getName();

      EntityInfo returnType = extractOperationReturnType(operation, package_).orElse(null);

      VisibilityType visibility = convert(operation.getVisibility(), package_);

      List<Modifier> modifiers = new ArrayList<>();
      if (operation.isAbstract()) {
        Modifier abstractModifier = new Modifier("abstract");
        modifiers.add(abstractModifier);
      }
      if (operation.isStatic()) {
        Modifier staticModifier = new Modifier("static");
        modifiers.add(staticModifier);
      }

      List<ClassOperationParameter> parameters = new ArrayList<>();
      for (Parameter operationParameter : operation.getOwnedParameters()) {
        if (operationParameter.getDirection() != ParameterDirectionKind.RETURN_LITERAL) {
          String operationParameterName = operationParameter.getName();
          ClassModelEntityInfo operationParameterType = null;
          if (operationParameter.getType() != null) {
            operationParameterType = extractOperationParameterType(operationParameter.getType(),
                package_);
          }
          boolean optional = false; // TODO: Future work: Optional can't be derived from GenMyModel
          ClassOperationParameter parameter = new ClassOperationParameter(operationParameterName,
              operationParameterType, optional);
          parameters.add(parameter);
        }
      }

      return new ClassOperation(name, returnType, visibility, modifiers, parameters);
    }

    static EntityInfo convert(ActivityNode activityNode,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        boolean includeTypeSpecificDetails) throws IllegalArgumentException, ServiceException {
      if (activityNode == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      if (activityNode instanceof Action) {
        return convert((Action) activityNode, package_, includeTypeSpecificDetails);
      } else if (activityNode instanceof ControlNode) {
        return convert((ControlNode) activityNode, package_, includeTypeSpecificDetails);
      }
      throw new ServiceException(
          "No conversion implemented for activity-node type '" + activityNode.getClass().getName()
              + "'");
    }

    static EntityInfo convert(Vertex vertex,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        boolean includeTypeSpecificDetails) throws IllegalArgumentException, ServiceException {
      if (vertex == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      if (vertex instanceof State) {
        return convert((State) vertex, package_, includeTypeSpecificDetails);
      } else if (vertex instanceof Pseudostate) {
        return convert((Pseudostate) vertex, package_, includeTypeSpecificDetails);
      }
      throw new ServiceException(
          "No conversion implemented for vertex type '" + vertex.getClass().getName() + "'");
    }

    static EntityInfo convert(ControlNode controlNode,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        boolean includeTypeSpecificDetails) throws IllegalArgumentException, ServiceException {
      if (controlNode == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      EntityInfo entityInfo;
      String name = controlNode.getName();
      if (includeTypeSpecificDetails) {
        NodeType type = getNodeType(controlNode, package_);
        entityInfo = new Node(name, package_, type);
      } else {
        entityInfo = new EntityInfo(name, package_);
      }
      return entityInfo;
    }

    static EntityInfo convert(State state,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        boolean includeTypeSpecificDetails) throws IllegalArgumentException {
      if (state == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      EntityInfo entityInfo;
      String name = state.getName();
      if (includeTypeSpecificDetails) {
        FunctionCall entryFunctionCall = extractFunctionCall(state.getEntry(), package_, null)
            .orElse(null);
        FunctionCall doActivityFunctionCall = extractFunctionCall(state.getDoActivity(), package_,
            null)
            .orElse(null);
        FunctionCall exitFunctionCall = extractFunctionCall(state.getExit(), package_, null)
            .orElse(null);
        if (state instanceof FinalState) {
          entityInfo = new at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.FinalState(
              name, package_, entryFunctionCall, doActivityFunctionCall, exitFunctionCall);
        } else {
          entityInfo = new at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.State(
              name, package_, entryFunctionCall, doActivityFunctionCall, exitFunctionCall);
        }
      } else {
        entityInfo = new EntityInfo(name, package_);
      }
      return entityInfo;
    }

    static EntityInfo convert(Pseudostate pseudostate,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_,
        boolean includeTypeSpecificDetails) throws IllegalArgumentException, ServiceException {
      if (pseudostate == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      EntityInfo entityInfo;
      String name = pseudostate.getName();
      if (includeTypeSpecificDetails) {
        PseudostateType type = convert(pseudostate.getKind(), package_);
        entityInfo = new at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.Pseudostate(
            name, package_, type);
      } else {
        entityInfo = new EntityInfo(name, package_);
      }
      return entityInfo;
    }

    static VisibilityType convert(VisibilityKind visibilityKind,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_)
        throws IllegalArgumentException {
      if (visibilityKind == null || package_ == null) {
        throw new IllegalArgumentException();
      }
      VisibilityType visibilityType;
      switch (visibilityKind.getName()) {
        case "public":
          visibilityType = VisibilityType.PUBLIC;
          break;
        case "private":
          visibilityType = VisibilityType.PRIVATE;
          break;
        case "protected":
          visibilityType = VisibilityType.PROTECTED;
          break;
        case "package":
          visibilityType = VisibilityType.PACKAGE;
          break;
        default:
          visibilityType = VisibilityType.PUBLIC;
      }
      return visibilityType;
    }

    static PseudostateType convert(PseudostateKind kind,
        at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package package_)
        throws IllegalArgumentException, ServiceException {
      if (package_ == null) {
        throw new IllegalArgumentException();
      }
      PseudostateType type;
      // GenMyModel has "a very special logic" on how to mark a pseudostate from type INITIAL
      if (kind == null) {
        type = PseudostateType.INITIAL;
      } else {
        switch (kind) {
          case INITIAL_LITERAL:
            type = PseudostateType.INITIAL;
            break;
          case DEEP_HISTORY_LITERAL:
            type = PseudostateType.DEEP_HISTORY;
            break;
          case SHALLOW_HISTORY_LITERAL:
            type = PseudostateType.SHALLOW_HISTORY;
            break;
          case JOIN_LITERAL:
            type = PseudostateType.JOIN;
            break;
          case FORK_LITERAL:
            type = PseudostateType.FORK;
            break;
          case JUNCTION_LITERAL:
            type = PseudostateType.JUNCTION;
            break;
          case CHOICE_LITERAL:
            type = PseudostateType.CHOICE;
            break;
          case ENTRY_POINT_LITERAL:
            type = PseudostateType.ENTRY_POINT;
            break;
          case EXIT_POINT_LITERAL:
            type = PseudostateType.EXIT_POINT;
            break;
          case TERMINATE_LITERAL:
            type = PseudostateType.TERMINATE;
            break;
          default:
            throw new ServiceException(
                "No conversion implemented for pseudostate-kind '" + kind + "'");
        }
      }
      return type;
    }
  }
}
