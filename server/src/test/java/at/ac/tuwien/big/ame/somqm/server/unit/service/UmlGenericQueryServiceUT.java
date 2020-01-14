package at.ac.tuwien.big.ame.somqm.server.unit.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import at.ac.tuwien.big.ame.somqm.server.dto.query.EntityInfo;
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
import at.ac.tuwien.big.ame.somqm.server.dto.query.RelationInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelUml2Content;
import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;
import at.ac.tuwien.big.ame.somqm.server.service.ServiceException;
import at.ac.tuwien.big.ame.somqm.server.service.Uml2ExtractorService;
import at.ac.tuwien.big.ame.somqm.server.service.UmlGenericQueryService;
import at.ac.tuwien.big.ame.somqm.server.service.UmlModelService;
import at.ac.tuwien.big.ame.somqm.server.service.UmlValidationService;
import at.ac.tuwien.big.ame.somqm.server.service.impl.Uml2GenMyModelExtractorServiceImpl;
import at.ac.tuwien.big.ame.somqm.server.service.impl.UmlGenericQueryServicImpl;
import at.ac.tuwien.big.ame.somqm.server.service.impl.UmlValidationServiceImpl;
import at.ac.tuwien.big.ame.somqm.server.util.TestHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class UmlGenericQueryServiceUT {

  private static long CLASS_MODEL_ID = 1;
  private static long ACTIVITY_MODEL_ID = 2;
  private static long STATEMACHINE_MODEL_ID = 3;
  private static long CLASS_MODEL_NESTED_PACKAGES_ID = 4;
  private static UmlModelService umlModelService;
  private static Uml2ExtractorService extractorService;

  private UmlGenericQueryService queryService;

  /**
   * Setup services - that are not tested in this class - only once and assume they work properly.
   */
  @BeforeAll
  static void setupBeforeAll() {
    umlModelService = mock(UmlModelService.class); // @Mock can't be used, as field is static.

    UmlValidationService validationService = new UmlValidationServiceImpl();
    when(umlModelService.loadModel(CLASS_MODEL_ID)).thenReturn(
        new UmlModelUml2Content(validationService.load(TestHelper.getValidClassModelContent()),
            UmlModelType.CLASS_MODEL));
    when(umlModelService.loadModel(ACTIVITY_MODEL_ID)).thenReturn(
        new UmlModelUml2Content(validationService.load(TestHelper.getValidActivityModelContent()),
            UmlModelType.ACTIVITY_MODEL));
    when(umlModelService.loadModel(STATEMACHINE_MODEL_ID)).thenReturn(new UmlModelUml2Content(
        validationService.load(TestHelper.getValidStateMachineModelContent()),
        UmlModelType.STATE_MACHINE_MODEL));
    when(umlModelService.loadModel(CLASS_MODEL_NESTED_PACKAGES_ID))
        .thenReturn(new UmlModelUml2Content(
            validationService.load(TestHelper.getValidClassModelWithNestedPackagesContent()),
            UmlModelType.CLASS_MODEL));

    extractorService = new Uml2GenMyModelExtractorServiceImpl();
  }

  @BeforeEach
  void setup() {
    queryService = new UmlGenericQueryServicImpl(umlModelService, extractorService);
  }

  @Nested
  class getAll {

    private GetAllRequest request;

    private void givenAValidClassModelRequest() {
      request.setModelId(CLASS_MODEL_ID);
    }

    private void givenAValidActivityModelRequest() {
      request.setModelId(ACTIVITY_MODEL_ID);
    }

    private void givenAValidStateMachineModelRequest() {
      request.setModelId(STATEMACHINE_MODEL_ID);
    }

    @BeforeEach
    void setup() {
      request = new GetAllRequest();
    }

    @Nested
    class WithValidClassModel {

      @Disabled("Future work")
      @Test
      void todo() throws ServiceException {
        // Arrange
        givenAValidClassModelRequest();

        // Act
        GetAllResult result = queryService.getAll(request);

        // Assert
        for (RelationInfo relation : result.getRelations()) {
          System.out.println(relation);
        }
        fail();
      }
    }

    @Nested
    class WithValidActivityModel {

      @Disabled("Future work")
      @Test
      void todo() throws ServiceException {
        // Arrange
        givenAValidActivityModelRequest();

        // Act
        GetAllResult result = queryService.getAll(request);

        // Assert
        for (RelationInfo relation : result.getRelations()) {
          System.out.println(relation);
        }
        fail();
      }
    }

    @Nested
    class WithValidStateMachineModel {

      @Disabled("Future work")
      @Test
      void todo() throws ServiceException {
        // Arrange
        givenAValidStateMachineModelRequest();

        // Act
        GetAllResult result = queryService.getAll(request);

        // Assert
        for (RelationInfo relation : result.getRelations()) {
          System.out.println(relation);
        }
        fail();
      }
    }
  }

  @Nested
  class getPackages {

    private GetPackagesRequest request;

    private void givenAValidClassModelRequest() {
      request.setModelId(CLASS_MODEL_ID);
    }

    private void givenAValidActivityModelRequest() {
      request.setModelId(ACTIVITY_MODEL_ID);
    }

    private void givenAValidStateMachineModelRequest() {
      request.setModelId(STATEMACHINE_MODEL_ID);
    }

    @BeforeEach
    void setup() {
      request = new GetPackagesRequest();
    }

    @Nested
    class WithValidClassModel {

      @Disabled("Future work")
      @Test
      void todo() {
        // Arrange
        givenAValidClassModelRequest();

        // Act
        GetPackagesResult result = queryService.getPackages(request);

        // Assert
        for (Package package_ : result.getPackages()) {
          System.out.println(package_);
        }
        fail();
      }
    }

    @Nested
    class WithValidActivityModel {

      @Disabled("Future work")
      @Test
      void todo() {
        // Arrange
        givenAValidActivityModelRequest();

        // Act
        GetPackagesResult result = queryService.getPackages(request);

        // Assert
        for (Package package_ : result.getPackages()) {
          System.out.println(package_);
        }
        fail();
      }
    }

    @Nested
    class WithValidStateMachineModel {

      @Disabled("Future work")
      @Test
      void todo() {
        // Arrange
        givenAValidStateMachineModelRequest();

        // Act
        GetPackagesResult result = queryService.getPackages(request);

        // Assert
        for (Package package_ : result.getPackages()) {
          System.out.println(package_);
        }
        fail();
      }
    }
  }

  @Nested
  class getEntities {

    private GetEntitiesRequest request;

    private void givenAValidClassModelRequest() {
      request.setModelId(CLASS_MODEL_ID);
    }

    private void givenAValidActivityModelRequest() {
      request.setModelId(ACTIVITY_MODEL_ID);
    }

    private void givenAValidStateMachineModelRequest() {
      request.setModelId(STATEMACHINE_MODEL_ID);
    }

    @BeforeEach
    void setup() {
      request = new GetEntitiesRequest();
    }

    @Nested
    class WithValidClassModel {

      @Disabled("Future work")
      @Test
      void todo() throws ServiceException {
        // Arrange
        givenAValidClassModelRequest();

        // Act
        GetEntitiesResult result = queryService.getEntities(request);

        // Assert
        for (EntityInfo entityInfo : result.getEntities()) {
          System.out.println(entityInfo);
        }
        fail();
      }
    }

    @Nested
    class WithValidActivityModel {

      @Disabled("Future work")
      @Test
      void todo() throws ServiceException {
        // Arrange
        givenAValidActivityModelRequest();

        // Act
        GetEntitiesResult result = queryService.getEntities(request);

        // Assert
        for (EntityInfo entityInfo : result.getEntities()) {
          System.out.println(entityInfo);
        }
        fail();
      }
    }

    @Nested
    class WithValidStateMachineModel {

      @Disabled("Future work")
      @Test
      void todo() throws ServiceException {
        // Arrange
        givenAValidStateMachineModelRequest();

        // Act
        GetEntitiesResult result = queryService.getEntities(request);

        // Assert
        for (EntityInfo entityInfo : result.getEntities()) {
          System.out.println(entityInfo);
        }
        fail();
      }
    }
  }

  @Nested
  class getRelations {

    private GetRelationsRequest request;

    private void givenAValidClassModelRequest() {
      request.setModelId(CLASS_MODEL_ID);
    }

    private void givenAValidActivityModelRequest() {
      request.setModelId(ACTIVITY_MODEL_ID);
    }

    private void givenAValidStateMachineModelRequest() {
      request.setModelId(STATEMACHINE_MODEL_ID);
    }

    @BeforeEach
    void setup() {
      request = new GetRelationsRequest();
    }

    @Nested
    class WithValidClassModel {

      @Disabled("Future work")
      @Test
      void todo() throws ServiceException {
        // Arrange
        givenAValidClassModelRequest();
        request.setIncludeTypeSpecificDetails(true);

        // Act
        GetRelationsResult result = queryService.getRelations(request);

        // Assert
        for (RelationInfo relation : result.getRelations()) {
          System.out.println(relation);
        }
        fail();
      }
    }

    @Nested
    class WithValidActivityModel {

      @Disabled("Future work")
      @Test
      void todo() throws ServiceException {
        // Arrange
        givenAValidActivityModelRequest();
        request.setIncludeTypeSpecificDetails(true);

        // Act
        GetRelationsResult result = queryService.getRelations(request);

        // Assert
        for (RelationInfo relation : result.getRelations()) {
          System.out.println(relation);
        }
        fail();
      }
    }

    @Nested
    class WithValidStateMachineModel {

      @Disabled("Future work")
      @Test
      void todo() throws ServiceException {
        // Arrange
        givenAValidStateMachineModelRequest();
        request.setIncludeTypeSpecificDetails(true);

        // Act
        GetRelationsResult result = queryService.getRelations(request);

        // Assert
        for (RelationInfo relation : result.getRelations()) {
          System.out.println(relation);
        }
        fail();
      }
    }
  }

  @Nested
  class getInitialState {

    private GetInitialStateRequest request;

    private void givenAValidActivityModelRequest() {
      request.setModelId(ACTIVITY_MODEL_ID);
    }

    private void givenAValidStateMachineModelRequest() {
      request.setModelId(STATEMACHINE_MODEL_ID);
    }

    @BeforeEach
    void setup() {
      request = new GetInitialStateRequest();
    }

    @Nested
    class WithValidActivityModel {

      @Disabled("Future work")
      @Test
      void todo() throws ServiceException {
        // Arrange
        givenAValidActivityModelRequest();

        // Act
        GetInitialStateResult result = queryService.getInitialState(request);

        // Assert
        System.out.println(result);
        fail();
      }
    }

    @Nested
    class WithValidStateMachineModel {

      @Disabled("Future work")
      @Test
      void todo() throws ServiceException {
        // Arrange
        givenAValidStateMachineModelRequest();

        // Act
        GetInitialStateResult result = queryService.getInitialState(request);

        // Assert
        System.out.println(result);
        fail();
      }
    }
  }

  @Nested
  class getFinalState {

    private GetFinalStateRequest request;

    private void givenAValidActivityModelRequest() {
      request.setModelId(ACTIVITY_MODEL_ID);
    }

    private void givenAValidStateMachineModelRequest() {
      request.setModelId(STATEMACHINE_MODEL_ID);
    }

    @BeforeEach
    void setup() {
      request = new GetFinalStateRequest();
    }

    @Nested
    class WithValidActivityModel {

      @Disabled("Future work")
      @Test
      void todo() throws ServiceException {
        // Arrange
        givenAValidActivityModelRequest();

        // Act
        GetFinalStateResult result = queryService.getFinalState(request);

        // Assert
        System.out.println(result);
        fail();
      }
    }

    @Nested
    class WithValidStateMachineModel {

      @Disabled("Future work")
      @Test
      void todo() throws ServiceException {
        // Arrange
        givenAValidStateMachineModelRequest();

        // Act
        GetFinalStateResult result = queryService.getFinalState(request);

        // Assert
        System.out.println(result);
        fail();
      }
    }
  }

}
