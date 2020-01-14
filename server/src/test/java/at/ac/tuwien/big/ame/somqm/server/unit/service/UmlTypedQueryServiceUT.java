package at.ac.tuwien.big.ame.somqm.server.unit.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Action;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Activity;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActionsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActionsResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActivitiesRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetActivitiesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetNodesRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.GetNodesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.Node;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassAttribute;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassOperation;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetAttributesRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetAttributesResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetOperationsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.GetOperationsResult;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.FunctionCall;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.GetFunctionCallsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.GetFunctionCallsResult;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelUml2Content;
import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;
import at.ac.tuwien.big.ame.somqm.server.service.ServiceException;
import at.ac.tuwien.big.ame.somqm.server.service.Uml2ExtractorService;
import at.ac.tuwien.big.ame.somqm.server.service.UmlModelService;
import at.ac.tuwien.big.ame.somqm.server.service.UmlTypedQueryService;
import at.ac.tuwien.big.ame.somqm.server.service.UmlValidationService;
import at.ac.tuwien.big.ame.somqm.server.service.impl.Uml2GenMyModelExtractorServiceImpl;
import at.ac.tuwien.big.ame.somqm.server.service.impl.UmlTypedQueryServiceImpl;
import at.ac.tuwien.big.ame.somqm.server.service.impl.UmlValidationServiceImpl;
import at.ac.tuwien.big.ame.somqm.server.util.TestHelper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class UmlTypedQueryServiceUT {

  private static long PROJECT_ID = 1;
  private static UmlModelService umlModelService;
  private static Uml2ExtractorService extractorService;

  private UmlTypedQueryService queryService;

  /**
   * Setup services - that are not tested in this class - only once and assume they work properly.
   */
  @BeforeAll
  static void setupBeforeAll() {
    umlModelService = mock(UmlModelService.class); // @Mock can't be used, as field is static.

    UmlValidationService validationService = new UmlValidationServiceImpl();
    when(umlModelService.loadModelFromProject(PROJECT_ID, UmlModelType.CLASS_MODEL)).thenReturn(
        new UmlModelUml2Content(validationService.load(TestHelper.getValidClassModelContent()),
            UmlModelType.CLASS_MODEL));
    when(umlModelService.loadModelFromProject(PROJECT_ID, UmlModelType.ACTIVITY_MODEL)).thenReturn(
        new UmlModelUml2Content(validationService.load(TestHelper.getValidActivityModelContent()),
            UmlModelType.ACTIVITY_MODEL));
    when(umlModelService.loadModelFromProject(PROJECT_ID, UmlModelType.STATE_MACHINE_MODEL))
        .thenReturn(new UmlModelUml2Content(
            validationService.load(TestHelper.getValidStateMachineModelContent()),
            UmlModelType.STATE_MACHINE_MODEL));

    extractorService = new Uml2GenMyModelExtractorServiceImpl();
  }

  @BeforeEach
  void setup() {
    queryService = new UmlTypedQueryServiceImpl(umlModelService, extractorService);
  }

  @Nested
  class getAttributes {

    private GetAttributesRequest request;

    private void givenAValidProjectRequest() {
      request.setProjectId(PROJECT_ID);
    }

    @BeforeEach
    void setup() {
      request = new GetAttributesRequest();
    }

    @Disabled("Future work")
    @Test
    void todo() {
      // Arrange
      givenAValidProjectRequest();

      // Act
      GetAttributesResult result = queryService.getAttributes(request);

      // Assert
      for (ClassAttribute attribute : result.getAttributes()) {
        System.out.println(attribute);
      }
      fail();
    }
  }

  @Nested
  class getOperations {

    private GetOperationsRequest request;

    private void givenAValidProjectRequest() {
      request.setProjectId(PROJECT_ID);
    }

    @BeforeEach
    void setup() {
      request = new GetOperationsRequest();
    }

    @Disabled("Future work")
    @Test
    void todo() {
      // Arrange
      givenAValidProjectRequest();

      // Act
      GetOperationsResult result = queryService.getOperations(request);

      // Assert
      for (ClassOperation operation : result.getOperations()) {
        System.out.println(operation);
      }
      fail();
    }
  }

  @Nested
  class getActivities {

    private GetActivitiesRequest request;

    private void givenAValidProjectRequest() {
      request.setProjectId(PROJECT_ID);
    }

    @BeforeEach
    void setup() {
      request = new GetActivitiesRequest();
    }

    @Disabled("Future work")
    @Test
    void todo() {
      // Arrange
      givenAValidProjectRequest();

      // Act
      GetActivitiesResult result = queryService.getActivities(request);

      // Assert
      for (Activity activity : result.getActivities()) {
        System.out.println(activity);
      }
      fail();
    }
  }

  @Nested
  class getActions {

    private GetActionsRequest request;

    private void givenAValidProjectRequest() {
      request.setProjectId(PROJECT_ID);
    }

    @BeforeEach
    void setup() {
      request = new GetActionsRequest();
    }

    @Disabled("Future work")
    @Test
    void todo() throws ServiceException {
      // Arrange
      givenAValidProjectRequest();

      // Act
      GetActionsResult result = queryService.getActions(request);

      // Assert
      for (Action action : result.getActions()) {
        System.out.println(action);
      }
      fail();
    }
  }

  @Nested
  class getNodes {

    private GetNodesRequest request;

    private void givenAValidProjectRequest() {
      request.setProjectId(PROJECT_ID);
    }

    @BeforeEach
    void setup() {
      request = new GetNodesRequest();
    }

    @Disabled("Future work")
    @Test
    void todo() throws ServiceException {
      // Arrange
      givenAValidProjectRequest();

      // Act
      GetNodesResult result = queryService.getNodes(request);

      // Assert
      for (Node node : result.getNodes()) {
        System.out.println(node);
      }
      fail();
    }
  }

  @Nested
  class getFunctionCalls {

    private GetFunctionCallsRequest request;

    private List<FunctionCall> expectedFunctionCalls(String... functionCallNames) {
      List<FunctionCall> functionCalls = new ArrayList<>();
      for (String functionCallName : functionCallNames) {
        FunctionCall functionCall = new FunctionCall(functionCallName);
        functionCalls.add(functionCall);
      }
      return functionCalls;
    }

    private void givenAValidProjectRequest() {
      request.setProjectId(PROJECT_ID);
    }

    @BeforeEach
    void setup() {
      request = new GetFunctionCallsRequest();
    }

    @Nested
    class invalidNameFilterRegex {

      @Test
      void invalidRegexShouldFail() {
        // Arrange
        givenAValidProjectRequest();
        request.setFunctionCallNameRegex("[");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
          queryService.getFunctionCalls(request);
        });
      }
    }

    @Nested
    class validNameFilterRegex {

      @Test
      void noFilterShouldWork() {
        // Arrange
        givenAValidProjectRequest();
        request.setFunctionCallNameRegex(null);

        // Act
        GetFunctionCallsResult result = queryService.getFunctionCalls(request);

        // Assert
        assertThat(result.getFunctionCalls(), containsInAnyOrder(
            expectedFunctionCalls(
                "myOnEventFunctionCall",
                "myEntryFunctionCall",
                "myExitFunctionCall",
                "myOnEventFunctionCall2",
                "myEntryFunctionCall2",
                "myExitFunctionCall2",
                "myEntryFunctionCall3",
                "myEffectFunctionCall",
                "myEffectFunctionCall2").toArray()
            )
        );
      }

      @Test
      void matchAllShouldWork() {
        // Arrange
        givenAValidProjectRequest();
        request.setFunctionCallNameRegex("^.*$");

        // Act
        GetFunctionCallsResult result = queryService.getFunctionCalls(request);

        // Assert
        assertThat(result.getFunctionCalls(), containsInAnyOrder(
            expectedFunctionCalls(
                "myOnEventFunctionCall",
                "myEntryFunctionCall",
                "myExitFunctionCall",
                "myOnEventFunctionCall2",
                "myEntryFunctionCall2",
                "myExitFunctionCall2",
                "myEntryFunctionCall3",
                "myEffectFunctionCall",
                "myEffectFunctionCall2").toArray()
            )
        );
      }

      @Test
      void startsWithMyEffectShouldWork() {
        // Arrange
        givenAValidProjectRequest();
        request.setFunctionCallNameRegex("^myEffect.*$");

        // Act
        GetFunctionCallsResult result = queryService.getFunctionCalls(request);

        // Assert
        assertThat(result.getFunctionCalls(), containsInAnyOrder(
            expectedFunctionCalls(
                "myEffectFunctionCall",
                "myEffectFunctionCall2").toArray()
            )
        );
      }

      @Test
      void endsWith2ShouldWork() {
        // Arrange
        givenAValidProjectRequest();
        request.setFunctionCallNameRegex("^.*2$");

        // Act
        GetFunctionCallsResult result = queryService.getFunctionCalls(request);

        // Assert
        assertThat(result.getFunctionCalls(), containsInAnyOrder(
            expectedFunctionCalls(
                "myOnEventFunctionCall2",
                "myEntryFunctionCall2",
                "myExitFunctionCall2",
                "myEffectFunctionCall2").toArray()
            )
        );
      }
    }
  }

}
