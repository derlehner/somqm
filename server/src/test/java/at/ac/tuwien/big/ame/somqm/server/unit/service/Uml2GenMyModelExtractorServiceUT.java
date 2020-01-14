package at.ac.tuwien.big.ame.somqm.server.unit.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import at.ac.tuwien.big.ame.somqm.server.dto.query.EntityInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.RelationInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Modifier;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Action;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Node;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.enumeration.ActionType;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.enumeration.NodeType;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassAttribute;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassModelEntityInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassOperation;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassOperationParameter;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.enumeration.ClassType;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.enumeration.VisibilityType;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.FunctionCall;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelUml2Content;
import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;
import at.ac.tuwien.big.ame.somqm.server.service.ServiceException;
import at.ac.tuwien.big.ame.somqm.server.service.Uml2ExtractorService;
import at.ac.tuwien.big.ame.somqm.server.service.UmlValidationService;
import at.ac.tuwien.big.ame.somqm.server.service.impl.Uml2GenMyModelExtractorServiceImpl;
import at.ac.tuwien.big.ame.somqm.server.service.impl.UmlValidationServiceImpl;
import at.ac.tuwien.big.ame.somqm.server.util.TestHelper;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.eclipse.uml2.uml.Pseudostate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class Uml2GenMyModelExtractorServiceUT {

  private static UmlValidationService validationService;
  private Uml2ExtractorService extractorService;
  private UmlModelUml2Content modelContent;

  /**
   * Setup services - that are not tested in this class - only once and assume they work properly.
   */
  @BeforeAll
  static void setupBeforeAll() {
    validationService = new UmlValidationServiceImpl();
  }

  @BeforeEach
  void setup() {
    extractorService = new Uml2GenMyModelExtractorServiceImpl();
    modelContent = null;
  }

  private void givenAValidClassModel() {
    byte[] content = TestHelper.getValidClassModelContent();
    modelContent = new UmlModelUml2Content(validationService.load(content),
        UmlModelType.CLASS_MODEL);
  }

  private void givenAValidClassModelWithNestedPackages() {
    byte[] content = TestHelper.getValidClassModelWithNestedPackagesContent();
    modelContent = new UmlModelUml2Content(validationService.load(content),
        UmlModelType.CLASS_MODEL);
  }

  private void givenAValidActivityModel() {
    byte[] content = TestHelper.getValidActivityModelContent();
    modelContent = new UmlModelUml2Content(validationService.load(content),
        UmlModelType.ACTIVITY_MODEL);
  }

  private void givenAValidStateMachineModel() {
    byte[] content = TestHelper.getValidStateMachineModelContent();
    modelContent = new UmlModelUml2Content(validationService.load(content),
        UmlModelType.STATE_MACHINE_MODEL);
  }

  @Nested
  class extractAll {

    // done in UmlGenericQueryServiceUT
  }

  @Nested
  class extractPackages {

    private Pattern packageNameFilter;

    @BeforeEach
    void setup() {
      packageNameFilter = null;
    }

    @Nested
    class WithValidClassModelWithNestedPackages {

      @BeforeEach
      void setup() {
        givenAValidClassModelWithNestedPackages();
      }

      @Test
      void shouldFindAllPackages() {
        // Arrange

        // Act
        List<Package> result = extractorService.extractPackages(modelContent, packageNameFilter);

        // Assert
        List<String> actual = result.stream().map(Package::getFullName)
            .collect(Collectors.toList());
        assertThat(actual, containsInAnyOrder(
            "",
            "myUberPackage",
            "myUberPackage.mySubPackage1",
            "myUberPackage.mySubPackage2",
            "myUberPackage.mySubPackage1.mySubSubPackage1"
        ));
      }

      @Test
      void shouldFindAllPackagesEndingWithPackage1() {
        // Arrange
        packageNameFilter = Pattern.compile("^.*Package1$");

        // Act
        List<Package> result = extractorService.extractPackages(modelContent, packageNameFilter);

        // Assert
        List<String> actual = result.stream().map(Package::getFullName)
            .collect(Collectors.toList());
        assertThat(actual, containsInAnyOrder(
            "myUberPackage.mySubPackage1",
            "myUberPackage.mySubPackage1.mySubSubPackage1"
        ));
      }
    }
  }

  @Nested
  class extractRelations {

    @Disabled("Future work")
    @Test
    void todo() {
      fail();
    }
  }

  @Nested
  class extractActivities {

    // not relevant for genMyModel
  }

  @Nested
  class extractFunctionCalls {

    // done in TypedQueryService
  }

  @Nested
  class extractRelationsInternal {

    // Add tests to check if result is correct if includeRelationTypeSpecificDetails is true
    // Add tests to check if results entities are correct (includeEntityTypeSpecificDetails true/false)
    // Add tests to check if result is correct if source-/target-entity-name filters are used

    @Nested
    class WithValidClassModel {

      @BeforeEach
      void setup() {
        givenAValidClassModel();
      }

      @Test
      void shouldFindAllRelations() throws ServiceException {
        // Arrange

        // Act
        List<RelationInfo> actual = Uml2GenMyModelExtractorServiceImpl
            .extractRelationsInternal(modelContent, false, false, null, null);

        // Assert
        assertEquals(5, actual.size());
      }

    }

    @Nested
    class WithValidActivityModel {

      @BeforeEach
      void setup() {
        givenAValidActivityModel();
      }

      @Test
      void shouldFindAllRelations() throws ServiceException {
        // Arrange

        // Act
        List<RelationInfo> actual = Uml2GenMyModelExtractorServiceImpl
            .extractRelationsInternal(modelContent, false, false, null, null);

        // Assert
        assertEquals(14, actual
            .size()); // Visible are only 12, but GenMyModel created 2 additional relations (which can be seen in the XMI file)
      }

    }

    @Nested
    class WithValidStateMachineModel {

      @BeforeEach
      void setup() {
        givenAValidStateMachineModel();
      }

      @Test
      void shouldFindAllRelations() throws ServiceException {
        // Arrange

        // Act
        List<RelationInfo> actual = Uml2GenMyModelExtractorServiceImpl
            .extractRelationsInternal(modelContent, false, false, null, null);

        // Assert
        assertEquals(19, actual.size());
      }

    }

  }


  @Nested
  class extractEntities {

    private String entityName;
    private Package entityPackage;

    private EntityInfo createEntity() {
      return new EntityInfo(entityName, entityPackage);
    }

    @Nested
    class WithValidClassModel {

      private String className;
      private Package classPackage;
      private ClassType classType;
      private List<ClassAttribute> classAttributes;
      private List<ClassOperation> classOperations;
      private List<Modifier> classModifiers;
      private VisibilityType classVisibility;
      private Pattern packageNameFilter;
      private Pattern entityNameFilter;

      @BeforeEach
      void setup() {
        givenAValidClassModel();
        classPackage = new Package("", null);
        classType = ClassType.DEFAULT;
        classVisibility = VisibilityType.PUBLIC;
        classAttributes = new LinkedList<>();
        classOperations = new LinkedList<>();
        classModifiers = new LinkedList<>();
        className = "";
        packageNameFilter = null;
        entityNameFilter = null;
      }

      private ClassModelEntityInfo createClassDiagram() {
        return new ClassModelEntityInfo(className, classPackage, classType, classAttributes,
            classOperations, classModifiers, classVisibility);
      }

      @Test
      void typeSpecificInfoFalse_assertEntityNamesCorrect() throws ServiceException {
        // Arrange
        List<EntityInfo> expectedActions = new LinkedList<>();
        entityName = "AbstractClass";
        entityPackage = new Package("TestPackage", null);
        expectedActions.add(createEntity());
        entityName = "Interface";
        entityPackage = new Package("", null);
        expectedActions.add(createEntity());
        entityName = "RelatedClass";
        expectedActions.add(createEntity());
        entityName = "SubClass";
        expectedActions.add(createEntity());
        int index = -1;

        // Act
        List<EntityInfo> actualEntities = extractorService
            .extractEntities(modelContent, false, packageNameFilter,
                entityNameFilter);

        // Assert
        for (EntityInfo expectedEntity : expectedActions) {
          index = actualEntities.indexOf(expectedEntity);
          assertNotEquals(-1, index);
        }
      }

      @Test
      void typeSpecificInfoFalse_assertAllEntitiesLoaded() throws ServiceException {
        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, false, packageNameFilter,
                entityNameFilter);

        // Assert
        assertEquals(4, entities.size());
      }

      @Test
      void typeSpecificInfoFalse_assertEntityPackageNameCorrect() throws ServiceException {
        // Arrange
        entityName = "AbstractClass";
        entityPackage = new Package("TestPackage", null);
        EntityInfo expectedEntity = createEntity();
        Package expectedPackage = entityPackage;

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, false, packageNameFilter,
                entityNameFilter);
        int index = entities.indexOf(expectedEntity);
        assertNotEquals(-1, index);
        EntityInfo actualEntity = entities.get(index);
        Package actualPackage = actualEntity.getPackage();

        // Assert
        assertEquals(expectedPackage.getName(), actualPackage.getName());
      }

      @Test
      void typeSpecificInfoTrue_assertAllEntitiesLoaded() throws ServiceException {
        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);

        // Assert
        assertEquals(4, entities.size());
      }

      @Test
      void typeSpecificInfoTrue_assertEntityNamesCorrect() throws ServiceException {
        // Arrange
        List<EntityInfo> expectedActions = new LinkedList<>();
        className = "AbstractClass";
        classPackage = new Package("TestPackage", null);
        expectedActions.add(createClassDiagram());
        className = "Interface";
        classPackage = new Package("", null);
        expectedActions.add(createClassDiagram());
        className = "RelatedClass";
        expectedActions.add(createClassDiagram());
        className = "SubClass";
        int index = -1;

        // Act
        List<EntityInfo> actualEntities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);

        // Assert
        for (EntityInfo expectedEntity : expectedActions) {
          index = actualEntities.indexOf(expectedEntity);
          assertNotEquals(-1, index);
        }
      }

      @Test
      void typeSpecificInfoTrue_assertEntityPackageNameCorrect() throws ServiceException {
        // Arrange
        className = "AbstractClass";
        classPackage = new Package("TestPackage", null);
        ClassModelEntityInfo expectedClass = createClassDiagram();

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);
        int index = entities.indexOf(expectedClass);
        assertNotEquals(-1, index);
        ClassModelEntityInfo actualClass = (ClassModelEntityInfo) entities.get(index);

        // Assert
        assertNotNull(actualClass);
        assertNotNull(actualClass.getPackage());
        assertEquals("TestPackage", actualClass.getPackage().getName());
      }

      @Test
      void typeSpecificInfoTrue_assertEntityNoPackageCorrect() throws ServiceException {
        // Arrange
        className = "Interface";
        ClassModelEntityInfo expectedClass = createClassDiagram();
        Package expectedPackage = new Package("", null);

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);
        int index = entities.indexOf(expectedClass);
        assertNotEquals(-1, index);
        ClassModelEntityInfo actualClass = (ClassModelEntityInfo) entities.get(index);

        // Assert
        assertNotNull(actualClass);
        assertEquals(expectedPackage, actualClass.getPackage());
      }

      @Test
      void typeSpecificInfoTrue_assertClassVisibilityInformationPackageCorrect()
          throws ServiceException {
        // Arrange
        className = "AbstractClass";
        classPackage = new Package("TestPackage", null);
        ClassModelEntityInfo expectedClass = createClassDiagram();

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);
        int index = entities.indexOf(expectedClass);
        assertNotEquals(-1, index);
        ClassModelEntityInfo actualClass = (ClassModelEntityInfo) entities.get(index);

        // Assert
        assertNotNull(actualClass);
        assertEquals(VisibilityType.PACKAGE, actualClass.getVisibility());
      }

      @Test
      void typeSpecificInfoTrue_assertClassVisibilityInformationPublicCorrect()
          throws ServiceException {
        // Arrange
        className = "Interface";
        ClassModelEntityInfo expectedClass = createClassDiagram();

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);
        int index = entities.indexOf(expectedClass);
        assertNotEquals(-1, index);
        ClassModelEntityInfo actualClass = (ClassModelEntityInfo) entities.get(index);

        // Assert
        assertNotNull(actualClass);
        assertEquals(VisibilityType.PUBLIC, actualClass.getVisibility());
      }

      @Test
      void typeSpecificInfoTrue_assertClassVisibilityInformationPrivateCorrect()
          throws ServiceException {
        // Arrange
        className = "RelatedClass";
        ClassModelEntityInfo expectedClass = createClassDiagram();

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);
        int index = entities.indexOf(expectedClass);
        assertNotEquals(-1, index);
        ClassModelEntityInfo actualClass = (ClassModelEntityInfo) entities.get(index);

        // Assert
        assertNotNull(actualClass);
        assertEquals(VisibilityType.PRIVATE, actualClass.getVisibility());
      }

      @Test
      void typeSpecificInfoTrue_assertClassVisibilityInformationProtectedCorrect()
          throws ServiceException {
        // Arrange
        className = "SubClass";
        ClassModelEntityInfo expectedClass = createClassDiagram();

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);
        int index = entities.indexOf(expectedClass);
        assertNotEquals(-1, index);
        ClassModelEntityInfo actualClass = (ClassModelEntityInfo) entities.get(index);

        // Assert
        assertNotNull(actualClass);
        assertEquals(VisibilityType.PROTECTED, actualClass.getVisibility());
      }

      @Test
      void typeSpecificInfoTrue_assertClassTypeAbstractCorrect() throws ServiceException {
        // Arrange
        className = "AbstractClass";
        classPackage = new Package("TestPackage", null);
        ClassModelEntityInfo expectedClass = createClassDiagram();

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);
        int index = entities.indexOf(expectedClass);
        assertNotEquals(-1, index);
        ClassModelEntityInfo actualClass = (ClassModelEntityInfo) entities.get(index);

        // Assert
        assertNotNull(actualClass);
        assertEquals(ClassType.ABSTRACT, actualClass.getType());
      }

      @Test
      void typeSpecificInfoTrue_assertClassTypeInterfaceCorrect() throws ServiceException {
        // Arrange
        className = "Interface";
        ClassModelEntityInfo expectedClass = createClassDiagram();

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);
        int index = entities.indexOf(expectedClass);
        assertNotEquals(-1, index);
        ClassModelEntityInfo actualClass = (ClassModelEntityInfo) entities.get(index);

        // Assert
        assertNotNull(actualClass);
        assertEquals(ClassType.INTERFACE, actualClass.getType());
      }

      @Test
      void typeSpecificInfoTrue_assertClassTypeDefaultCorrect() throws ServiceException {
        // Arrange
        className = "SubClass";
        ClassModelEntityInfo expectedClass = createClassDiagram();

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);
        int index = entities.indexOf(expectedClass);
        assertNotEquals(-1, index);
        ClassModelEntityInfo actualClass = (ClassModelEntityInfo) entities.get(index);

        // Assert
        assertNotNull(actualClass);
        assertEquals(ClassType.DEFAULT, actualClass.getType());
      }

      @Test
      @Disabled("Future work: Define class with no modifier")
      void typeSpecificInfoTrue_assertNoClassModifiersCorrect() throws ServiceException {
        // Arrange
        className = "SubClass";
        ClassModelEntityInfo expectedClass = createClassDiagram();

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);
        int index = entities.indexOf(expectedClass);
        assertNotEquals(-1, index);
        ClassModelEntityInfo actualClass = (ClassModelEntityInfo) entities.get(index);
        List<Modifier> emptyModifiersList = actualClass.getModifiers();

        // Assert
        assertNotNull(emptyModifiersList);
        assertEquals(0, emptyModifiersList.size());
      }

      @Test
      @Disabled("Can't be modelled with GenMyModel")
      void typeSpecificInfoTrue_assertOneClassModifierCorrect() throws ServiceException {
        // Arrange
        className = "SubClass";
        ClassModelEntityInfo expectedClass = createClassDiagram();
        fail();

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);
        int index = entities.indexOf(expectedClass);
        assertNotEquals(index, -1);
        ClassModelEntityInfo actualClass = (ClassModelEntityInfo) entities.get(index);
        List<Modifier> emptyModifiersList = actualClass.getModifiers();

        // Assert
        assertNotNull(emptyModifiersList);
        assertEquals(emptyModifiersList.size(), 1);
      }

      @Test
      @Disabled("Can't be modelled with GenMyModel")
      void typeSpecificInfoTrue_assertMultipleClassModifiersCorrect() throws ServiceException {
        // Arrange
        className = "SubClass";
        ClassModelEntityInfo expectedClass = createClassDiagram();
        fail();

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);
        int index = entities.indexOf(expectedClass);
        assertNotEquals(index, -1);
        ClassModelEntityInfo actualClass = (ClassModelEntityInfo) entities.get(index);
        List<Modifier> emptyModifiersList = actualClass.getModifiers();

        // Assert
        assertNotNull(emptyModifiersList);
        assertEquals(emptyModifiersList.size(), 2);
      }

      @Test
      @Disabled("Can't be modelled with GenMyModel")
      void typeSpecificInfoTrue_assertClassModifierStaticCorrect() throws ServiceException {
        // Arrange
        className = "SubClass";
        ClassModelEntityInfo expectedClass = createClassDiagram();
        fail();

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);
        int index = entities.indexOf(expectedClass);
        assertNotEquals(index, -1);
        ClassModelEntityInfo actualClass = (ClassModelEntityInfo) entities.get(index);
        List<Modifier> modifiers = actualClass.getModifiers();

        // Assert
        assertNotNull(modifiers);
        assertEquals(modifiers.size(), 1);
        assertEquals(modifiers.get(0).getName(), "static");
      }

      @Test
      @Disabled("Can't be modelled with GenMyModel")
      void typeSpecificInfoTrue_assertClasModifierFinalCorrect() throws ServiceException {
        // Arrange
        className = "SubClass";
        ClassModelEntityInfo expectedClass = createClassDiagram();
        fail();

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);
        int index = entities.indexOf(expectedClass);
        assertNotEquals(index, -1);
        ClassModelEntityInfo actualClass = (ClassModelEntityInfo) entities.get(index);
        List<Modifier> modifiers = actualClass.getModifiers();

        // Assert
        assertNotNull(modifiers);
        assertEquals(modifiers.size(), 1);
        assertEquals(modifiers.get(0).getName(), "final");
      }

      @Test
      public void assertEntityFilterCorrect() throws ServiceException {
        entityName = "AbstractClass";
        entityPackage = new Package("TestPackage", null);
        EntityInfo expectedEntity = createEntity();
        entityNameFilter = Pattern.compile("AbstractClass");

        // Act
        List<EntityInfo> attributes = extractorService
            .extractEntities(modelContent, false, packageNameFilter, entityNameFilter);
        int index = attributes.indexOf(expectedEntity);

        // Assert
        assertNotEquals(-1, index);
      }

      @Test
      public void assertPackageFilterCorrect() throws ServiceException {
        entityName = "AbstractClass";
        entityPackage = new Package("TestPackage", null);
        EntityInfo expectedEntity = createEntity();
        packageNameFilter = Pattern.compile("TestPackage");
        entityNameFilter = Pattern.compile("AbstractClass");

        // Act
        List<EntityInfo> attributes = extractorService
            .extractEntities(modelContent, false, packageNameFilter, entityNameFilter);
        int index = attributes.indexOf(expectedEntity);

        // Assert
        assertNotEquals(-1, index);
      }

      @Test
      public void assertFilterNonExistingPackageFails() {
        // Arrange
        packageNameFilter = Pattern.compile("IllegalPackage");
        entityNameFilter = Pattern.compile("AbstractClass");

        // Act + Assert
        assertThrows(NoSuchElementException.class, () -> {
          extractorService
              .extractEntities(modelContent, false, packageNameFilter, entityNameFilter);
        });
      }
    }

    @Nested
    class WithValidActivityModel {

      private String actionName;
      private Package actionPackage;
      private ActionType actionType;
      private Pattern packageNameFilter;
      private Pattern entityNameFilter;
      private String nodeName;
      private Package nodePackage;
      private NodeType nodeType;
      private Pattern activityNameFilter;
      private Pattern nodeNameFilter;
      private NodeType nodeTypeFilter;

      @BeforeEach
      void setup() {
        givenAValidActivityModel();
        actionName = "";
        actionPackage = new Package("", null);
        actionType = ActionType.OPAQUE_ACTION;
        packageNameFilter = null;
        entityNameFilter = null;
        nodeName = "";
        nodePackage = new Package("", null);
        nodeType = null;  // NodeType has to be explicitly set in all tests involving nodes
      }

      private Node createNode() {
        return new Node(nodeName, nodePackage, nodeType);
      }

      private Action createAction() {
        return new Action(actionName, actionPackage, actionType);
      }

      @Test
      void typeSpecificInfoFalse_assertEntityNamesCorrect() throws ServiceException {
        // Arrange
        entityPackage = new Package("", null);
        List<EntityInfo> expectedActions = new LinkedList<>();
        entityName = "myAction1";
        expectedActions.add((EntityInfo) createEntity());
        entityName = "myAction2";
        expectedActions.add((EntityInfo) createEntity());
        entityName = "myAction3A";
        expectedActions.add((EntityInfo) createEntity());
        entityName = "myAction3B";
        expectedActions.add((EntityInfo) createEntity());
        entityName = "myAction4";
        expectedActions.add((EntityInfo) createEntity());
        int index = -1;

        // Act
        List<EntityInfo> actualEntities = extractorService
            .extractEntities(modelContent, false, packageNameFilter, entityNameFilter);

        // Assert
        for (EntityInfo expectedAction : expectedActions) {
          index = actualEntities.indexOf(expectedAction);
          assertNotEquals(-1, index);
        }
      }

      @Test
      void typeSpecificInfoFalse_assertAllEntitiesLoaded() throws ServiceException {
        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, false, packageNameFilter,
                entityNameFilter);

        // Assert
        assertEquals(16, entities.size());
      }

      @Test
      @Disabled("No appropriate Test Data defined")
      void typeSpecificInfoFalse_assertSuperPackageNameCorrect() throws ServiceException {
        // Arrange
        Package expectedPackage = new Package("superPackage", null);
        Package actualPackage = null;

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, false, packageNameFilter,
                entityNameFilter);
        actualPackage = entities.get(0).getPackage();
        assertNotNull(actualPackage);
        while (actualPackage.getSuperPackage() != null) {
          actualPackage = actualPackage.getSuperPackage();
        }

        // Assert
        assertEquals(expectedPackage.getName(), actualPackage.getName());
      }

      @Test
      @Disabled("No Action with Package defined yet")
      void typeSpecificInfoFalse_assertEntityPackageNameCorrect() throws ServiceException {
        // Arrange
        actionName = "";
        EntityInfo expectedEntity = createAction();
        Package expectedPackage = new Package("", null);
        fail();

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, false, packageNameFilter,
                entityNameFilter);
        int index = entities.indexOf(expectedEntity);
        EntityInfo actualEntity = entities.get(index);
        Package actualPackage = actualEntity.getPackage();

        // Assert
        assertEquals(expectedPackage.getName(), actualPackage.getName());
      }

      @Test
      void typeSpecificInfoTrue_assertCorrectActionsLoaded() throws ServiceException {
        // Arrange
        List<Action> expectedActions = new LinkedList<>();
        actionName = "myAction1";
        expectedActions.add(createAction());
        actionName = "myAction2";
        expectedActions.add(createAction());
        actionName = "myAction3A";
        expectedActions.add(createAction());
        actionName = "myAction3B";
        expectedActions.add(createAction());
        actionName = "myAction4";
        expectedActions.add(createAction());
        int index = -1;

        // Act
        List<EntityInfo> actualEntities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);

        // Assert
        for (EntityInfo expectedEntity : expectedActions) {
          index = actualEntities.indexOf(expectedEntity);
          assertNotEquals(-1, index);
        }
      }

      @Test
      void typeSpecificInfoTrue_assertCorrectNodesLoaded() throws ServiceException {
        // Arrange
        List<Node> expectedNodes = new LinkedList<>();
        nodeName = "myInitialNode";
        nodeType = NodeType.INITIAL;
        expectedNodes.add(createNode());
        nodeName = "myFinalNode";
        nodeType = NodeType.ACTIVITY_FINAL;
        expectedNodes.add(createNode());
        nodeName = "FlowFinalNode";
        nodeType = NodeType.FLOW_FINAL;
        expectedNodes.add(createNode());
        nodeName = "myJoinNode";
        nodeType = NodeType.SYNCHRONIZATION;
        expectedNodes.add(createNode());
        nodeName = "myMergeNode";
        nodeType = NodeType.DECISION_MERGE;
        expectedNodes.add(createNode());
        nodeName = "myDecisionNode";
        nodeType = NodeType.DECISION_SPLIT;
        expectedNodes.add(createNode());
        nodeName = "myForkNode";
        nodeType = NodeType.FORK;
        expectedNodes.add(createNode());
        int index = -1;

        // Act
        List<EntityInfo> actualEntities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);

        // Assert
        for (EntityInfo expectedNode : expectedNodes) {
          index = actualEntities.indexOf(expectedNode);
          assertNotEquals(-1, index);
        }
      }

      @Test
      public void assertEntityFilterCorrect() throws ServiceException {
        entityName = "myAction1";
        entityPackage = new Package("", null);
        EntityInfo expectedEntity = createEntity();
        entityNameFilter = Pattern.compile("myAction1");

        // Act
        List<EntityInfo> attributes = extractorService
            .extractEntities(modelContent, false, packageNameFilter, entityNameFilter);
        int index = attributes.indexOf(expectedEntity);

        // Assert
        assertNotEquals(-1, index);
      }

      @Test
      @Disabled("Not applicable to GenMyModel")
      public void assertPackageFilterCorrect() throws ServiceException {
        entityName = "myAction1";
        entityPackage = new Package("TestPackage", null);
        EntityInfo expectedEntity = createEntity();
        packageNameFilter = Pattern.compile("TestPackage");
        entityNameFilter = Pattern.compile("AbstractClass");

        // Act
        List<EntityInfo> attributes = extractorService
            .extractEntities(modelContent, false, packageNameFilter, entityNameFilter);
        int index = attributes.indexOf(expectedEntity);

        // Assert
        assertNotEquals(-1, index);
      }

      @Test
      @Disabled("Not applicable to GenMyModel")
      public void assertFilterNonExistingPackageFails() {
        // Arrange
        packageNameFilter = Pattern.compile("IllegalPackage");
        entityNameFilter = Pattern.compile("myAction1");

        // Act + Assert
        assertThrows(NoSuchElementException.class, () -> {
          extractorService
              .extractEntities(modelContent, false, packageNameFilter, entityNameFilter);
        });
      }
    }

    @Nested
    class WithValidStateMachineModel {

      at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.State expectedState;
      at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.State actualState;
      String statename;
      Package statePackage;
      FunctionCall entry;
      FunctionCall doActivity;
      FunctionCall exit;
      Pattern packageNameFilter;
      Pattern entityNameFilter;

      @BeforeEach
      void setup() {
        statePackage = new Package("", null);
        entry = null;
        doActivity = null;
        exit = null;
        packageNameFilter = null;
        entityNameFilter = null;
        givenAValidStateMachineModel();
      }

      private at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.State createState() {
        return new at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.State(
            statename, statePackage, entry, doActivity, exit);
      }

      private Pseudostate createPseudoState() {
        return null;
      }

      @Test
      void typeSpecificInfoFalse_assertEntityNamesCorrect() throws ServiceException {
        // Arrange
        entityPackage = new Package("", null);
        List<EntityInfo> expectedStates = new LinkedList<>();
        entityName = "myInitializationState";
        expectedStates.add(createEntity());
        entityName = "myState1";
        expectedStates.add(createEntity());
        entityName = "myUberState";
        expectedStates.add(createEntity());
        entityName = "myState2";
        expectedStates.add(createEntity());
        entityName = "myState3A";
        expectedStates.add(createEntity());
        entityName = "myState3B";
        expectedStates.add(createEntity());
        entityName = "myState4";
        expectedStates.add(createEntity());
        entityName = "myErrorState";
        expectedStates.add(createEntity());
        entityName = "myLastState";
        expectedStates.add(createEntity());
        int index = -1;

        // Act
        List<EntityInfo> actualEntities = extractorService
            .extractEntities(modelContent, false, packageNameFilter,
                entityNameFilter);

        // Assert
        for (EntityInfo expectedEntity : expectedStates) {
          index = actualEntities.indexOf(expectedEntity);
          assertNotEquals(-1, index);
        }
      }

      @Test
      void typeSpecificInfoFalse_assertAllEntitiesLoaded() throws ServiceException {
        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, false, packageNameFilter,
                entityNameFilter);

        // Assert
        assertEquals(19, entities.size());
      }

      @Test
      @Disabled("Not applicable to GenMyModel")
      void typeSpecificInfoFalse_assertSuperPackageNameCorrect() throws ServiceException {
        // Arrange
        Package expectedPackage = new Package("superpackage", null);
        Package actualPackage = null;

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, false, packageNameFilter,
                entityNameFilter);
        actualPackage = entities.get(0).getPackage();
        assertNotNull(actualPackage);
        while (actualPackage.getSuperPackage() != null) {
          actualPackage = actualPackage.getSuperPackage();
        }

        // Assert
        assertEquals(expectedPackage.getName(), actualPackage.getName());
      }

      @Test
      @Disabled("Not applicable to GenMyModel")
      void typeSpecificInfoFalse_assertEntityPackageNameCorrect() throws ServiceException {
        // Arrange
        statePackage = new Package("", null);
        statename = "";
        EntityInfo expectedEntity = createState();
        statename = "";

        // Act
        List<EntityInfo> entities = extractorService
            .extractEntities(modelContent, false, packageNameFilter,
                entityNameFilter);
        int index = entities.indexOf(expectedEntity);
        assertNotEquals(-1, index);
        EntityInfo actualEntity = entities.get(index);
        Package actualPackage = actualEntity.getPackage();

        // Assert
        assertEquals(statePackage.getName(), actualPackage.getName());
      }

      @Test
      void typeSpecificInfoTrue_assertStateNamesCorrect() throws ServiceException {
        // Arrange
        List<EntityInfo> expectedStates = new LinkedList<>();
        statename = "myInitializationState";
        expectedStates.add(createState());
        statename = "myUberState";
        expectedStates.add(createState());
        statename = "myState3A";
        expectedStates.add(createState());
        statename = "myState3B";
        expectedStates.add(createState());
        statename = "myState4";
        expectedStates.add(createState());
        statename = "myErrorState";
        expectedStates.add(createState());
        statename = "myLastState";
        expectedStates.add(createState());
        statename = "myState1";
        entry = new FunctionCall("myEntryFunctionCall");
        doActivity = new FunctionCall("myOnEventFunctionCall");
        exit = new FunctionCall("myExitFunctionCall");
        expectedStates.add(createState());
        statename = "myState2";
        entry = new FunctionCall("myEntryFunctionCall2");
        doActivity = new FunctionCall("myOnEventFunctionCall2");
        exit = new FunctionCall("myExitFunctionCall2");
        expectedStates.add(createState());
        int index = -1;

        // Act
        List<EntityInfo> actualEntities = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);

        // Assert
        for (EntityInfo expectedEntity : expectedStates) {
          index = actualEntities.indexOf(expectedEntity);
          assertNotEquals(-1, index);
        }
      }

      @Test
      void typeSpecificInfoTrue_assertStateEntryCallCorrect() throws ServiceException {
        entry = new FunctionCall("myEntryFunctionCall");
        doActivity = new FunctionCall("myOnEventFunctionCall");
        exit = new FunctionCall("myExitFunctionCall");
        statename = "myState1";
        expectedState = createState();

        // Act
        List<EntityInfo> result = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);
        int index = result.indexOf(expectedState);
        assertNotEquals(-1, index);
        actualState = (at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.State) result
            .get(index);

        // Assert
        assertEquals(expectedState.getEntryFunctionCall().getName(),
            actualState.getEntryFunctionCall().getName());
      }

      @Test
      void typeSpecificInfoTrue_assertStateDoCallCorrect() throws ServiceException {
        entry = new FunctionCall("myEntryFunctionCall");
        doActivity = new FunctionCall("myOnEventFunctionCall");
        exit = new FunctionCall("myExitFunctionCall");
        statename = "myState1";
        expectedState = createState();

        // Act
        List<EntityInfo> result = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);
        int index = result.indexOf(expectedState);
        assertNotEquals(-1, index);
        actualState = (at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.State) result
            .get(index);

        // Assert
        assertEquals(expectedState.getDoActivityFunctionCall().getName(),
            actualState.getDoActivityFunctionCall().getName());
      }

      @Test
      void typeSpecificInfoTrue_assertExitCallCorrect() throws ServiceException {
        entry = new FunctionCall("myEntryFunctionCall2");
        doActivity = new FunctionCall("myOnEventFunctionCall2");
        exit = new FunctionCall("myExitFunctionCall2");
        statename = "myState2";
        expectedState = createState();

        // Act
        List<EntityInfo> result = extractorService
            .extractEntities(modelContent, true, packageNameFilter,
                entityNameFilter);
        int index = result.indexOf(expectedState);
        assertNotEquals(-1, index);
        actualState = (at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.State) result
            .get(index);

        // Assert
        assertEquals(expectedState.getExitFunctionCall().getName(),
            actualState.getExitFunctionCall().getName());
      }

      @Test
      public void assertEntityFilterCorrect() throws ServiceException {
        entityName = "myState3A";
        entityPackage = new Package("", null);
        EntityInfo expectedEntity = createEntity();
        entityNameFilter = Pattern.compile("myState3A");

        // Act
        List<EntityInfo> attributes = extractorService
            .extractEntities(modelContent, false, packageNameFilter, entityNameFilter);
        int index = attributes.indexOf(expectedEntity);

        // Assert
        assertNotEquals(-1, index);
      }

      @Test
      @Disabled("Not applicable to GenMyModel")
      public void assertPackageFilterCorrect() throws ServiceException {
        entityName = "myAction1";
        entityPackage = new Package("TestPackage", null);
        EntityInfo expectedEntity = createEntity();
        packageNameFilter = Pattern.compile("TestPackage");
        entityNameFilter = Pattern.compile("AbstractClass");

        // Act
        List<EntityInfo> attributes = extractorService
            .extractEntities(modelContent, false, packageNameFilter, entityNameFilter);
        int index = attributes.indexOf(expectedEntity);

        // Assert
        assertNotEquals(-1, index);
      }

      @Test
      @Disabled("Not applicable to GenMyModel")
      public void assertFilterNonExistingPackageFails() {
        // Arrange
        packageNameFilter = Pattern.compile("IllegalPackage");
        entityNameFilter = Pattern.compile("myAction1");

        // Act + Assert
        assertThrows(NoSuchElementException.class, () -> {
          extractorService
              .extractEntities(modelContent, false, packageNameFilter, entityNameFilter);
        });
      }
    }

  }

  @Nested
  class extractAttributes {

    private ClassModelEntityInfo createBasicClassEntity() {
      return new ClassModelEntityInfo("", new Package("", null), ClassType.DEFAULT,
          new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), VisibilityType.PUBLIC);
    }

    @Nested
    class WithValidClassModel {

      private ClassAttribute expectedAttribute;
      private ClassAttribute actualAttribute;
      private String attributeName;
      private ClassModelEntityInfo attributeType;
      private VisibilityType attributeVisibiity;
      private List<Modifier> attributeModifiers;
      private Pattern classFilter;
      private Pattern attributeNameFilter;

      @BeforeEach
      void setup() {
        givenAValidClassModel();
        attributeName = "";
        attributeType = createBasicClassEntity();
        attributeVisibiity = VisibilityType.PUBLIC;
        attributeModifiers = new LinkedList<>();
        classFilter = null;
        attributeNameFilter = null;
      }

      private ClassAttribute createAttribute() {
        return new ClassAttribute(attributeName, attributeType, attributeVisibiity,
            attributeModifiers);
      }

      @Test
      void assertAttributeNameCorrect() {
        // Arrange
        attributeName = "abstractAttribute";
        expectedAttribute = createAttribute();

        // Act
        List<ClassAttribute> attributes = extractorService
            .extractAttributes(modelContent, classFilter, attributeNameFilter);
        int index = attributes.indexOf(expectedAttribute);

        // Assert
        assertNotEquals(-1, index);
      }

      @Test
      void assertNumberOfAttributesCorrect() {
        // Arrange
        int expectedNumber = 7;   // 5 different attributes + 2 due to inheritence

        // Act
        List<ClassAttribute> attributes = extractorService
            .extractAttributes(modelContent, classFilter, attributeNameFilter);

        // Assert
        assertEquals(expectedNumber, attributes.size());
      }

      @Test
      void assertAttributeTypeCorrect() {
        // Arrange
        attributeName = "relatedClass";
        expectedAttribute = createAttribute();
        ClassModelEntityInfo expectedType = new ClassModelEntityInfo("RelatedClass",
            new Package("", null), ClassType.DEFAULT, new LinkedList<ClassAttribute>(),
            new LinkedList<ClassOperation>(), new LinkedList<Modifier>(), VisibilityType.PUBLIC);

        // Act
        List<ClassAttribute> attributes = extractorService
            .extractAttributes(modelContent, classFilter, attributeNameFilter);
        int index = attributes.indexOf(expectedAttribute);
        assertNotEquals(-1, index);
        actualAttribute = attributes.get(index);

        // Assert
        assertEquals(expectedType.getName(), actualAttribute.getType().getName());
      }

      @Test
      void assertAttributeVisibilityPublicCorrect() {
        // Arrange
        attributeName = "abstractAttribute";
        expectedAttribute = createAttribute();

        // Act
        List<ClassAttribute> attributes = extractorService
            .extractAttributes(modelContent, classFilter, attributeNameFilter);
        int index = attributes.indexOf(expectedAttribute);
        assertNotEquals(-1, index);
        actualAttribute = attributes.get(index);

        // Assert
        assertNotNull(actualAttribute.getVisibility());
        assertEquals(VisibilityType.PUBLIC, actualAttribute.getVisibility());
      }

      @Test
      void assertAttributeVisibilityPrivateCorrect() {
        // Arrange
        attributeName = "privateStaticAttribute";
        expectedAttribute = createAttribute();

        // Act
        List<ClassAttribute> attributes = extractorService
            .extractAttributes(modelContent, classFilter, attributeNameFilter);
        int index = attributes.indexOf(expectedAttribute);
        assertNotEquals(-1, index);
        actualAttribute = attributes.get(index);

        // Assert
        assertNotNull(actualAttribute.getVisibility());
        assertEquals(VisibilityType.PRIVATE, actualAttribute.getVisibility());
      }

      @Test
      void assertAttributeVisibilityProtectedCorrect() {
        // Arrange
        attributeName = "protectedStaticAbstractAttribute";
        expectedAttribute = createAttribute();

        // Act
        List<ClassAttribute> attributes = extractorService
            .extractAttributes(modelContent, classFilter, attributeNameFilter);
        int index = attributes.indexOf(expectedAttribute);
        assertNotEquals(-1, index);
        actualAttribute = attributes.get(index);

        // Assert
        assertNotNull(actualAttribute.getVisibility());
        assertEquals(VisibilityType.PROTECTED, actualAttribute.getVisibility());
      }

      @Test
      void assertAttributeVisibilityPackageCorrect() {
        // Arrange
        attributeName = "packageFinalAttribute";
        expectedAttribute = createAttribute();

        // Act
        List<ClassAttribute> attributes = extractorService
            .extractAttributes(modelContent, classFilter, attributeNameFilter);
        int index = attributes.indexOf(expectedAttribute);
        assertNotEquals(-1, index);
        actualAttribute = attributes.get(index);

        // Assert
        assertNotNull(actualAttribute.getVisibility());
        assertEquals(VisibilityType.PACKAGE, actualAttribute.getVisibility());
      }

      @Test
      @Disabled("Can't be modelled with GenMyModel")
      void assertAttributeModifierStaticCorrect() {
        // Arrange
        attributeName = "privateStaticAttribute";
        expectedAttribute = createAttribute();

        // Act
        List<ClassAttribute> attributes = extractorService
            .extractAttributes(modelContent, classFilter, attributeNameFilter);
        int index = attributes.indexOf(expectedAttribute);
        assertNotEquals(-1, index);
        actualAttribute = attributes.get(index);

        // Assert
        assertNotNull(actualAttribute.getModifiers());
        assertEquals(1, actualAttribute.getModifiers().size());
        assertEquals("static", actualAttribute.getModifiers().get(0).getName());
      }

      @Test
      @Disabled("Can't be modelled with GenMyModel")
      void assertAttributeModifierAbstractCorrect() {
        // Arrange
        attributeName = "privateStaticAttribute";
        expectedAttribute = createAttribute();

        // Act
        List<ClassAttribute> attributes = extractorService
            .extractAttributes(modelContent, classFilter, attributeNameFilter);
        int index = attributes.indexOf(expectedAttribute);
        assertNotEquals(-1, index);
        actualAttribute = attributes.get(index);

        // Assert
        assertNotNull(actualAttribute.getModifiers());
        assertEquals(1, actualAttribute.getModifiers().size());
        assertEquals("abstract", actualAttribute.getModifiers().get(0).getName());
      }

      @Test
      @Disabled("Can't be modelled with GenMyModel")
      void assertAttributeModifierFinalCorrect() {
        // Arrange
        attributeName = "privateStaticAttribute";
        expectedAttribute = createAttribute();

        // Act
        List<ClassAttribute> attributes = extractorService
            .extractAttributes(modelContent, classFilter, attributeNameFilter);
        int index = attributes.indexOf(expectedAttribute);
        assertNotEquals(index, -1);
        actualAttribute = attributes.get(index);

        // Assert
        assertNotNull(actualAttribute.getModifiers());
        assertEquals(1, actualAttribute.getModifiers().size());
        assertEquals("final", actualAttribute.getModifiers().get(0).getName());
      }

      @Test
      @Disabled("Can't be modelled with GenMyModel")
      void assertSeveralAttributeModifiersCorrect() {
        // Arrange
        attributeName = "privateStaticAttribute";
        expectedAttribute = createAttribute();

        // Act
        List<ClassAttribute> attributes = extractorService
            .extractAttributes(modelContent, classFilter, attributeNameFilter);
        int index = attributes.indexOf(expectedAttribute);
        assertNotEquals(-1, index);
        actualAttribute = attributes.get(index);

        // Assert
        assertNotNull(actualAttribute.getModifiers());
        assertEquals(2, actualAttribute.getModifiers().size());
      }

      @Test
      public void assertAttributeFilterCorrect() {
        attributeName = "relatedClass";
        expectedAttribute = createAttribute();
        attributeNameFilter = Pattern.compile("relatedClass");

        // Act
        List<ClassAttribute> attributes = extractorService
            .extractAttributes(modelContent, classFilter, attributeNameFilter);
        int index = attributes.indexOf(expectedAttribute);

        // Assert
        assertNotEquals(-1, index);
      }

      @Test
      public void assertClassFilterCorrect() {
        attributeName = "relatedClass";
        expectedAttribute = createAttribute();
        classFilter = Pattern.compile("AbstractClass");

        // Act
        List<ClassAttribute> attributes = extractorService
            .extractAttributes(modelContent, classFilter, attributeNameFilter);
        int index = attributes.indexOf(expectedAttribute);

        // Assert
        assertNotEquals(-1, index);
      }

      @Test
      public void assertFilterNonExistingEntityFails() {
        // Arrange
        classFilter = Pattern.compile("IllegalClass");
        attributeNameFilter = Pattern.compile("relatedClass");

        // Act + Assert
        assertThrows(NoSuchElementException.class, () -> {
          extractorService
              .extractAttributes(modelContent, classFilter, attributeNameFilter);
        });
      }
    }
  }

  @Nested
  class extractOperations {

    @Nested
    class WithValidClassModel {

      private ClassOperation expectedOperation;
      private ClassOperation actualOperation;
      private String operationName;
      private EntityInfo operationReturnType;
      private VisibilityType operationVisibility;
      private List<Modifier> operationModifiers;
      private List<ClassOperationParameter> operationParameters;
      private Pattern classFilter;
      private Pattern operationNameFilter;

      @BeforeEach
      void setup() {
        givenAValidClassModel();
        operationReturnType = null;
        operationVisibility = VisibilityType.PUBLIC;
        operationModifiers = new LinkedList<>();
        operationParameters = new LinkedList<>();
        classFilter = null;
        operationNameFilter = null;
      }

      private ClassOperation createOperation() {
        return new ClassOperation(operationName, operationReturnType, operationVisibility,
            operationModifiers, operationParameters);
      }

      @Test
      void assertOperationNameCorrect() {
        // Arrange
        operationName = "abstractOperation";
        operationModifiers.add(new Modifier("abstract"));
        expectedOperation = createOperation();

        // Act
        List<ClassOperation> operations = extractorService
            .extractOperations(modelContent, classFilter, operationNameFilter);
        int index = operations.indexOf(expectedOperation);

        // Assert
        assertNotEquals(-1, index);
      }

      @Test
      @Disabled("Can't be modelled with GenMyModel")
      void assertOperationModifierStaticCorrect() {
        // Arrange
        operationName = "privateStaticMethod";
        expectedOperation = createOperation();

        // Act
        List<ClassOperation> operations = extractorService
            .extractOperations(modelContent, classFilter, operationNameFilter);
        int index = operations.indexOf(expectedOperation);
        assertNotEquals(-1, index);
        actualOperation = operations.get(index);

        // Assert
        assertNotNull(actualOperation.getModifiers());
        assertEquals(1, actualOperation.getModifiers().size());
        assertEquals("static", actualOperation.getModifiers().get(0).getName());
      }

      @Test
      @Disabled("Can't be modelled with GenMyModel")
      void assertOperationModifierFinalCorrect() {
        // Arrange
        operationName = "packageFinalParameterMethod";
        expectedOperation = createOperation();

        // Act
        List<ClassOperation> operations = extractorService
            .extractOperations(modelContent, classFilter, operationNameFilter);
        int index = operations.indexOf(expectedOperation);
        assertNotEquals(index, -1);
        actualOperation = operations.get(index);

        // Assert
        assertNotNull(actualOperation.getModifiers());
        assertEquals(actualOperation.getModifiers().size(), 1);
        assertEquals(actualOperation.getModifiers().get(0).getName(), "final");
      }

      @Test
      void assertOperationModifierAbstractCorrect() {
        // Arrange
        operationName = "abstractOperation";
        operationModifiers.add(new Modifier("abstract"));
        expectedOperation = createOperation();

        // Act
        List<ClassOperation> operations = extractorService
            .extractOperations(modelContent, classFilter, operationNameFilter);
        int index = operations.indexOf(expectedOperation);
        assertNotEquals(index, -1);
        actualOperation = operations.get(index);

        // Assert
        assertNotNull(actualOperation.getModifiers());
        assertEquals(1, actualOperation.getModifiers().size());
        assertEquals("abstract", actualOperation.getModifiers().get(0).getName());
      }

      @Test
      @Disabled("cannot be modelled with GenMyModel")
      void assertSeveralOperationModifiersCorrect() {
        // Arrange
        operationReturnType = new ClassModelEntityInfo("SubClass", new Package("", null),
            ClassType.DEFAULT,
            new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), VisibilityType.PROTECTED);
        operationName = "protectedStaticFinalReturnTypeMethod";
        expectedOperation = createOperation();

        // Act
        List<ClassOperation> operations = extractorService
            .extractOperations(modelContent, classFilter, operationNameFilter);
        int index = operations.indexOf(expectedOperation);
        assertNotEquals(index, -1);
        actualOperation = operations.get(index);

        // Assert
        assertNotNull(actualOperation.getModifiers());
        assertEquals(actualOperation.getModifiers().size(), 2);
      }

      @Test
      void assertOperationVisibilityPublicCorrect() {
        // Arrange
        operationName = "abstractOperation";
        operationModifiers.add(new Modifier("abstract"));
        expectedOperation = createOperation();

        // Act
        List<ClassOperation> operations = extractorService
            .extractOperations(modelContent, classFilter, operationNameFilter);
        int index = operations.indexOf(expectedOperation);
        assertNotEquals(-1, index);
        actualOperation = operations.get(index);

        // Assert
        assertNotNull(actualOperation.getModifiers());
        assertEquals(VisibilityType.PUBLIC, actualOperation.getVisibility());
      }

      @Test
      @Disabled("This information cannot be retrieved from GenMyModel")
      void assertOperationVisibilityPrivateCorrect() {
        // Arrange
        operationName = "privateStaticMethod";
        expectedOperation = createOperation();

        // Act
        List<ClassOperation> operations = extractorService
            .extractOperations(modelContent, classFilter, operationNameFilter);
        int index = operations.indexOf(expectedOperation);
        assertNotEquals(index, -1);
        actualOperation = operations.get(index);

        // Assert
        assertNotNull(actualOperation.getModifiers());
        assertEquals(actualOperation.getVisibility(), VisibilityType.PRIVATE);
      }

      @Test
      @Disabled("This information cannot be retrieved from GenMyModel")
      void assertOperationVisibilityProtectedCorrect() {
        // Arrange
        operationReturnType = new ClassModelEntityInfo("SubClass", new Package("", null),
            ClassType.DEFAULT,
            new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), VisibilityType.PUBLIC);
        operationName = "protectedStaticFinalReturnTypeMethod";
        operationVisibility = VisibilityType.PROTECTED;
        expectedOperation = createOperation();

        // Act
        List<ClassOperation> operations = extractorService
            .extractOperations(modelContent, classFilter, operationNameFilter);
        int index = operations.indexOf(expectedOperation);
        assertNotEquals(index, -1);
        actualOperation = operations.get(index);

        // Assert
        assertEquals(actualOperation.getVisibility(), expectedOperation.getVisibility());
      }

      @Test
      @Disabled("This information cannot be retrieved from GenMyModel")
      void assertOperationVisibilityPackageCorrect() {
        // Arrange
        operationName = "packageFinalParameterMethod";
        expectedOperation = createOperation();

        // Act
        List<ClassOperation> operations = extractorService
            .extractOperations(modelContent, classFilter, operationNameFilter);
        int index = operations.indexOf(expectedOperation);
        assertNotEquals(index, -1);
        actualOperation = operations.get(index);

        // Assert
        assertNotNull(actualOperation.getModifiers());
        assertEquals(actualOperation.getVisibility(), VisibilityType.PACKAGE);
      }

      @Test
      void assertOperationReturnTypeCorrect() {
        // Arrange
        operationReturnType = new EntityInfo("SubClass", new Package("", null));
        operationName = "protectedStaticFinalReturnTypeMethod";
        expectedOperation = createOperation();

        // Act
        List<ClassOperation> operations = extractorService
            .extractOperations(modelContent, classFilter, operationNameFilter);
        int index = operations.indexOf(expectedOperation);
        assertNotEquals(-1, index);
        actualOperation = operations.get(index);

        // Assert
        assertNotNull(actualOperation.getReturnType());
        assertEquals(expectedOperation.getReturnType(), actualOperation.getReturnType());
      }

      @Test
      void assertOperationParameterNameCorrect() {
        // Arrange
        operationName = "packageFinalParameterMethod";
        ClassModelEntityInfo operationParameterType = new ClassModelEntityInfo("Interface",
            new Package("", null), ClassType.INTERFACE,
            new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), VisibilityType.PUBLIC);
        operationParameters.add(new ClassOperationParameter("firstParam", operationParameterType,
            false));
        operationParameters.add(new ClassOperationParameter("secondParam", null,
            false));
        operationReturnType = null;
        expectedOperation = createOperation();

        // Act
        List<ClassOperation> operations = extractorService
            .extractOperations(modelContent, classFilter, operationNameFilter);
        int index = operations.indexOf(expectedOperation);
        assertNotEquals(-1, index);
        actualOperation = operations.get(index);
        assertNotNull(actualOperation.getParameters());
        index = actualOperation.getParameters().indexOf(expectedOperation.getParameters().get(0));
        ClassOperationParameter actualParameter = actualOperation.getParameters().get(index);

        // Assert
        assertEquals(expectedOperation.getParameters().get(0).getName(),
            actualParameter.getName());
      }

      @Test
      @Disabled("Can't be modelled with GenMyModel")
      void assertOperationParameterOptionalTrueCorrect() {
        fail();
      }

      @Test
      @Disabled("Can't be modelled with GenMyModel")
      void assertOperationParameterOptionalFalseCorrect() {
        fail();
      }

      @Test
      void assertOperationParameterTypeCorrect() {
        // Arrange
        operationName = "packageFinalParameterMethod";
        ClassModelEntityInfo operationParameterType = new ClassModelEntityInfo("Interface",
            new Package("", null), ClassType.INTERFACE,
            new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), VisibilityType.PUBLIC);
        operationParameters.add(new ClassOperationParameter("firstParam", operationParameterType,
            false));
        operationParameters.add(new ClassOperationParameter("secondParam", null,
            false));
        operationReturnType = null;
        expectedOperation = createOperation();

        // Act
        List<ClassOperation> operations = extractorService
            .extractOperations(modelContent, classFilter, operationNameFilter);
        int index = operations.indexOf(expectedOperation);
        assertNotEquals(-1, index);
        actualOperation = operations.get(index);
        assertNotNull(actualOperation.getParameters());
        index = actualOperation.getParameters().indexOf(expectedOperation.getParameters().get(0));
        ClassOperationParameter actualParameter = actualOperation.getParameters().get(index);

        // Assert
        assertEquals(expectedOperation.getParameters().get(0).getName(),
            actualParameter.getName());
      }

      @Test
      public void assertOperationFilterCorrect() {
        operationName = "abstractOperation";
        expectedOperation = createOperation();
        operationNameFilter = Pattern.compile("abstractOperation");

        // Act
        List<ClassOperation> attributes = extractorService
            .extractOperations(modelContent, classFilter, operationNameFilter);
        int index = attributes.indexOf(expectedOperation);

        // Assert
        assertNotEquals(-1, index);
      }

      @Test
      public void assertClassFilterCorrect() {
        operationName = "abstractOperation";
        expectedOperation = createOperation();
        classFilter = Pattern.compile("AbstractClass");

        // Act
        List<ClassOperation> attributes = extractorService
            .extractOperations(modelContent, classFilter, operationNameFilter);
        int index = attributes.indexOf(expectedOperation);

        // Assert
        assertNotEquals(-1, index);
      }

      @Test
      public void assertFilterNonExistingEntityFails() {
        // Arrange
        classFilter = Pattern.compile("IllegalClass");
        operationNameFilter = Pattern.compile("abstractOperation");

        // Act + Assert
        assertThrows(NoSuchElementException.class, () -> {
          extractorService
              .extractOperations(modelContent, classFilter, operationNameFilter);
        });
      }
    }

  }

  @Nested
  class extractActions {

    @Nested
    class WithValidActivityModel {

      private Action expectedAction;
      private Action actualAction;
      private String actionName;
      private Package actionPackage;
      private ActionType actionType;
      private Pattern packageNameFilter;
      private Pattern activityNameFilter;
      private Pattern actionNameFilter;

      @BeforeEach
      void setup() {
        actionName = "";
        actionPackage = new Package("", null);
        actionType = ActionType.OPAQUE_ACTION;
        packageNameFilter = null;
        activityNameFilter = null;
        actionNameFilter = null;
        givenAValidActivityModel();
      }

      private Action createAction() {
        return new Action(actionName, actionPackage, actionType);
      }


      @Test
      void assertActionNameCorrect() throws ServiceException {
        // Arrange
        List<EntityInfo> expectedActions = new LinkedList<>();
        actionName = "myAction1";
        expectedActions.add(createAction());
        actionName = "myAction2";
        expectedActions.add(createAction());
        actionName = "myAction3A";
        expectedActions.add(createAction());
        actionName = "myAction3B";
        expectedActions.add(createAction());
        actionName = "myAction4";
        expectedActions.add(createAction());
        int index = -1;

        // Act
        List<Action> actualEntities = extractorService
            .extractActions(modelContent, packageNameFilter, activityNameFilter,
                actionNameFilter);

        // Assert
        for (EntityInfo expectedAction : expectedActions) {
          index = actualEntities.indexOf(expectedAction);
          assertNotEquals(-1, index);
        }
      }

      @Test
      void assertNumberOfLoadedActionsCorrect() throws ServiceException {
        // Arrange
        int expectedResultSize = 5;

        // Act
        List<Action> actualEntities = extractorService
            .extractActions(modelContent, packageNameFilter, activityNameFilter,
                actionNameFilter);

        // Assert
        assertEquals(expectedResultSize, actualEntities.size());
      }

      @Test
      void assertActionTypeCorrect() throws ServiceException {
        // Arrange
        actionName = "myAction1";
        actionType = ActionType.OPAQUE_ACTION;
        expectedAction = createAction();

        // Act
        List<Action> actualEntities = extractorService
            .extractActions(modelContent, packageNameFilter, activityNameFilter,
                actionNameFilter);
        int index = actualEntities.indexOf(expectedAction);
        assertNotEquals(-1, index);
        actualAction = actualEntities.get(index);

        // Assert
        assertEquals(expectedAction.getType(), actualAction.getType());
      }

      @Test
      public void assertActionFilterCorrect() throws ServiceException {
        actionName = "myAction2";
        expectedAction = createAction();
        actionNameFilter = Pattern.compile("myAction2");

        // Act
        List<Action> actions = extractorService
            .extractActions(modelContent, packageNameFilter, activityNameFilter, actionNameFilter);
        int index = actions.indexOf(expectedAction);

        // Assert
        assertNotEquals(-1, index);
      }

      @Test
      public void assertActivityFilterCorrect() throws ServiceException {
        actionName = "myAction2";
        expectedAction = createAction();
        activityNameFilter = Pattern.compile("Activity");

        // Act
        List<Action> actions = extractorService
            .extractActions(modelContent, packageNameFilter, activityNameFilter, actionNameFilter);
        int index = actions.indexOf(expectedAction);

        // Assert
        assertNotEquals(-1, index);
      }

      @Test
      public void assertFilterNonExistingActivityFails() {
        // Arrange
        actionName = "myAction2";
        expectedAction = createAction();
        activityNameFilter = Pattern.compile("IllegalActivity");
        actionNameFilter = Pattern.compile("myAction2");

        // Act + Assert
        assertThrows(NoSuchElementException.class, () -> {
          extractorService
              .extractActions(modelContent, packageNameFilter, activityNameFilter,
                  actionNameFilter);
        });
      }

      @Test
      public void assertFilterNonExistingPackageFails() {
        // Arrange
        actionName = "myAction2";
        expectedAction = createAction();
        packageNameFilter = Pattern.compile("IllegalPackage");
        actionNameFilter = Pattern.compile("myAction2");

        // Act + Assert
        assertThrows(NoSuchElementException.class, () -> {
          extractorService
              .extractActions(modelContent, packageNameFilter, activityNameFilter,
                  actionNameFilter);
        });
      }
    }
  }

  @Nested
  class extractNodes {

    @Nested
    class WithValidActivityModel {

      private String nodeName;
      private Package nodePackage;
      private NodeType nodeType;
      private Pattern packageNameFilter;
      private Pattern activityNameFilter;
      private Pattern nodeNameFilter;
      private NodeType nodeTypeFilter;

      @BeforeEach
      void setup() {
        givenAValidActivityModel();
        nodeName = "";
        nodePackage = new Package("", null);
        nodeType = null;  // NodeType has to be explicitly set in all tests involving nodes
        packageNameFilter = null;
        activityNameFilter = null;
        nodeNameFilter = null;
        nodeTypeFilter = null;
      }

      private Node createNode() {
        return new Node(nodeName, nodePackage, nodeType);
      }

      @Test
      void assertCorrectNodesLoaded() throws ServiceException {
        // Arrange
        List<Node> expectedNodes = new LinkedList<>();
        nodeName = "myInitialNode";
        nodeType = NodeType.INITIAL;
        expectedNodes.add(createNode());
        nodeName = "myFinalNode";
        nodeType = NodeType.ACTIVITY_FINAL;
        expectedNodes.add(createNode());
        nodeName = "FlowFinalNode";
        nodeType = NodeType.FLOW_FINAL;
        expectedNodes.add(createNode());
        nodeName = "myJoinNode";
        nodeType = NodeType.SYNCHRONIZATION;
        expectedNodes.add(createNode());
        nodeName = "myMergeNode";
        nodeType = NodeType.DECISION_MERGE;
        expectedNodes.add(createNode());
        nodeName = "myDecisionNode";
        nodeType = NodeType.DECISION_SPLIT;
        expectedNodes.add(createNode());
        nodeName = "myForkNode";
        nodeType = NodeType.FORK;
        expectedNodes.add(createNode());
        int index = -1;

        // Act
        List<Node> actualEntities = extractorService
            .extractNodes(modelContent, packageNameFilter, activityNameFilter, nodeNameFilter,
                nodeTypeFilter);

        // Assert
        for (EntityInfo expectedNode : expectedNodes) {
          index = actualEntities.indexOf(expectedNode);
          assertNotEquals(-1, index);
        }
      }

      @Test
      void assertNumberOfLoadedNodesCorrect() throws ServiceException {
        // Arrange
        int expectedResultSize = 10;

        // Act
        List<Node> actualNodes = extractorService
            .extractNodes(modelContent, packageNameFilter, activityNameFilter,
                nodeNameFilter, nodeTypeFilter);

        // Assert
        assertEquals(expectedResultSize, actualNodes.size());
      }

      @Test
      public void assertNodeFilterCorrect() throws ServiceException {
        // Arrange
        nodeName = "myFinalNode";
        nodeType = NodeType.ACTIVITY_FINAL;
        Node expectedNode = createNode();
        nodeNameFilter = Pattern.compile("myFinalNode");

        // Act
        List<Node> actions = extractorService
            .extractNodes(modelContent, packageNameFilter, activityNameFilter, nodeNameFilter,
                nodeTypeFilter);
        int index = actions.indexOf(expectedNode);

        // Assert
        assertNotEquals(-1, index);
      }

      @Test
      public void assertNodeTypeFilterCorrect() throws ServiceException {
        // Arrange
        nodeName = "myFinalNode";
        nodeType = NodeType.ACTIVITY_FINAL;
        Node expectedNode = createNode();
        nodeTypeFilter = NodeType.ACTIVITY_FINAL;

        // Act
        List<Node> actions = extractorService
            .extractNodes(modelContent, packageNameFilter, activityNameFilter, nodeNameFilter,
                nodeTypeFilter);
        int index = actions.indexOf(expectedNode);

        // Assert
        assertNotEquals(-1, index);
      }

      @Test
      public void assertActivityFilterCorrect() throws ServiceException {
        // Arrange
        nodeName = "myFinalNode";
        nodeType = NodeType.ACTIVITY_FINAL;
        Node expectedNode = createNode();
        activityNameFilter = Pattern.compile("Activity");

        // Act
        List<Node> actions = extractorService
            .extractNodes(modelContent, packageNameFilter, activityNameFilter, nodeNameFilter,
                nodeTypeFilter);
        int index = actions.indexOf(expectedNode);

        // Assert
        assertNotEquals(-1, index);
      }

      @Test
      public void assertFilterNonExistingActivityFails() {
        // Arrange
        activityNameFilter = Pattern.compile("IllegalActivity");
        nodeNameFilter = Pattern.compile("myFinalNode");

        // Act + Assert
        assertThrows(NoSuchElementException.class, () -> {
          extractorService
              .extractNodes(modelContent, packageNameFilter, activityNameFilter, nodeNameFilter,
                  nodeTypeFilter);
        });
      }

      @Test
      public void assertFilterNonExistingPackageFails() {
        // Arrange
        packageNameFilter = Pattern.compile("IllegalPackage");
        nodeNameFilter = Pattern.compile("myFinalNode");

        // Act + Assert
        assertThrows(NoSuchElementException.class, () -> {
          extractorService
              .extractNodes(modelContent, packageNameFilter, activityNameFilter, nodeNameFilter,
                  nodeTypeFilter);
        });
      }


    }
  }

}
