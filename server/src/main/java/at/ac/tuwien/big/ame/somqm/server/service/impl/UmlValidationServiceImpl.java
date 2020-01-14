package at.ac.tuwien.big.ame.somqm.server.service.impl;

import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;
import at.ac.tuwien.big.ame.somqm.server.service.ServiceException;
import at.ac.tuwien.big.ame.somqm.server.service.UmlValidationService;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage.Registry;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.URIConverter.ReadableInputStream;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UMLPackage.Literals;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UmlValidationServiceImpl implements UmlValidationService {

  private final Map<UmlModelType, TypeIdentifier> typeIdentificationMapping;

  @Autowired
  public UmlValidationServiceImpl() {
    typeIdentificationMapping = new HashMap<>();
    typeIdentificationMapping.put(UmlModelType.ACTIVITY_MODEL, new ActivityModelIdentifier());
    typeIdentificationMapping
        .put(UmlModelType.STATE_MACHINE_MODEL, new StateMachineModelIdentifier());
    typeIdentificationMapping.put(UmlModelType.CLASS_MODEL, new ClassModelIdentifier());
  }

  private static ResourceSet createResourceSet() {
    ResourceSet resourceSet = new ResourceSetImpl();

    UMLResourcesUtil.init(resourceSet);
    Registry packageRegistry = resourceSet.getPackageRegistry();
    packageRegistry.put("http://www.omg.org/spec/UML/20090901",
        UMLPackage.eINSTANCE); // Required to load models from http://www.omgwiki.org/model-interchange/doku.php?id=start
    packageRegistry.put("http://schema.omg.org/spec/UML/2.1", UMLPackage.eINSTANCE);
    packageRegistry.put(" http://www.omg.org/spec/UML/20131001", UMLPackage.eINSTANCE);
    packageRegistry
        .put("http://schema.omg.org/spec/UML/2.1/uml.xml#Integer", UMLPackage.PRIMITIVE_TYPE);

    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
        UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
        "xmi", UMLResource.Factory.INSTANCE);

    return resourceSet;
  }

  @Override
  public Model load(byte[] content) throws IllegalArgumentException {
    if (content == null || content.length == 0) {
      throw new IllegalArgumentException("Supplied content is invalid");
    }

    ResourceSet resourceSet = createResourceSet();
    URI modelUri = URI.createURI("dummyURI.uml");
    Resource resource = resourceSet.createResource(modelUri);
    ByteArrayInputStream bais = new ByteArrayInputStream(content);
    try (BufferedReader bfReader = new BufferedReader(
        new InputStreamReader(bais, StandardCharsets.UTF_8))) {
      try (ReadableInputStream readableInputStream = new URIConverter.ReadableInputStream(
          bfReader)) {
        resource.load(readableInputStream, Collections.emptyMap());
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to create Ecore resource from supplied content",
          e);
    }

    UMLResource umlResource = (UMLResource) resource;
    Model model = (Model) EcoreUtil
        .getObjectByType(umlResource.getContents(), UMLPackage.Literals.MODEL);
    /*
     * Model can be null e.g. when
     *  o set xmlns:uml is not registered in createResourceSet() or
     *  o if there exists no 'uml:Model' element in the provided file on the top level
     */
    if (model == null) {
      throw new IllegalArgumentException("Failed to extract UML model from supplied content");
    }

    return model;
  }

  @Override
  public UmlModelType validate(Model model, List<UmlModelType> supportedTypes)
      throws IllegalArgumentException, ServiceException {
    if (model == null) {
      throw new IllegalArgumentException("Supplied model is invalid");
    }
    if (supportedTypes == null || supportedTypes.isEmpty()) {
      throw new IllegalArgumentException("Supplied supported-types are invalid");
    }

    /*
     * TODO: Future work: Implement validation according to UML meta model
     * E.g. each reference must have a start- and end-entity.
     */

    /*
     * TODO: Future work: Improve model-type deriving
     * Currently very basic:
     * If there are activities: activity diagram.
     * If there are states or transitions: state-machine diagram.
     * Else: class diagram.
     */
    for (UmlModelType potentialType : supportedTypes) {
      TypeIdentifier typeIdentifier = typeIdentificationMapping.get(potentialType);
      if (typeIdentifier.checkTypeIsApplicable(model)) {
        return potentialType;
      }
    }
    throw new ServiceException("Type of model not supported");

    /*
     * TODO: Future work: Implement validation according to derived UML diagram type
     * E.g. a class diagram must not contain any activities.
     */
  }

  @Override
  public void verifyConsistency(Model model, UmlModelType type,
      Map<Model, UmlModelType> otherModels) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Supplied model is invalid");
    }
    if (type == null) {
      throw new IllegalArgumentException("Supplied type is invalid");
    }
    if (otherModels == null) {
      throw new IllegalArgumentException("Supplied other-models are invalid");
    }

    if (otherModels.isEmpty()) {
      return;
    }

    /*
     * TODO: Future work: Implement consistency verification
     *
     * throw new IllegalArgumentException("Supplied model is not consistent with the supplied other models");
     */
  }

  private abstract class TypeIdentifier {

    /**
     * @param model Must not be null.
     */
    abstract boolean checkTypeIsApplicable(Model model);
  }

  private class ClassModelIdentifier extends TypeIdentifier {

    @Override
    boolean checkTypeIsApplicable(Model model) {
      // check if EClass is on top level
      Class cl = (Class) EcoreUtil
          .getObjectByType(model.eContents(), Literals.CLASS);
      if (cl != null && cl.eClass().getName().equals("Class")) {
        return true;
      }
      Package pck = (Package) EcoreUtil
          .getObjectByType(model.eContents(), Literals.PACKAGE);
      // check for potential EClass inside packages
      while (pck != null) {
        cl = (Class) EcoreUtil
            .getObjectByType(pck.eContents(), Literals.CLASS);
        if (cl != null && cl.eClass().getName().equals("Class")) {
          return true;
        }
        pck = (Package) EcoreUtil
            .getObjectByType(pck.eContents(), Literals.PACKAGE);  // maybe inside nested package?
      }
      return false;
    }
  }

  private class ActivityModelIdentifier extends TypeIdentifier {

    @Override
    boolean checkTypeIsApplicable(Model model) {
      // check if EClass is on top level
      if (EcoreUtil
          .getObjectByType(model.eContents(), Literals.ACTIVITY) != null) {
        return true;
      }
      Package pck = (Package) EcoreUtil
          .getObjectByType(model.eContents(), Literals.PACKAGE);
      // check for potential EClass inside packages
      while (pck != null) {
        if (EcoreUtil
            .getObjectByType(pck.eContents(), Literals.ACTIVITY) != null) {
          return true;
        }
        pck = (Package) EcoreUtil
            .getObjectByType(pck.eContents(), Literals.PACKAGE);  // maybe inside nested package?
      }
      return false;
    }
  }

  private class StateMachineModelIdentifier extends TypeIdentifier {

    @Override
    boolean checkTypeIsApplicable(Model model) {
      if (EcoreUtil
          .getObjectByType(model.eContents(), Literals.STATE_MACHINE) != null) {
        return true;
      }
      Package pck = (Package) EcoreUtil
          .getObjectByType(model.eContents(), Literals.PACKAGE);
      // check for activities inside packages
      while (pck != null) {
        if (EcoreUtil.getObjectByType(pck.eContents(), Literals.STATE_MACHINE)
            != null) {  // activity found
          return true;
        }
        pck = (Package) EcoreUtil
            .getObjectByType(pck.eContents(), Literals.PACKAGE);  // maybe inside nested package?
      }
      return false;
    }
  }
}
