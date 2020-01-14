package at.ac.tuwien.big.ame.somqm.server.integration.service;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;

import at.ac.tuwien.big.ame.somqm.server.dto.query.GetAllRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.query.GetAllResult;
import at.ac.tuwien.big.ame.somqm.server.service.ServiceException;
import at.ac.tuwien.big.ame.somqm.server.service.Uml2ExtractorService;
import at.ac.tuwien.big.ame.somqm.server.service.UmlGenericQueryService;
import at.ac.tuwien.big.ame.somqm.server.service.UmlModelService;
import at.ac.tuwien.big.ame.somqm.server.service.impl.UmlGenericQueryServicImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UmlGenericQueryServiceIT {

  private static long CLASS_MODEL_ID = 1;
  private static long ACTIVITY_MODEL_ID = 2;
  private static long STATEMACHINE_MODEL_ID = 3;
  private static long CLASS_MODEL_NESTED_PACKAGES_ID = 4;
  @Mock
  private UmlModelService umlModelService;
  @Mock
  private Uml2ExtractorService extractorService;

  private UmlGenericQueryService queryService;

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

    private void givenAValidClassModelWithNestedPackages() {
      request.setModelId(CLASS_MODEL_NESTED_PACKAGES_ID);
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

    @Disabled("Future work")
    @Test
    void todo() throws ServiceException {
      // Arrange
      givenAValidClassModelRequest();

      // Act
      GetAllResult result = queryService.getAll(request);

      // Assert
      verify(umlModelService).loadModel(CLASS_MODEL_ID);
      fail();
    }
  }

  @Nested
  class getPackages {

    @Disabled("Future work")
    @Test
    void todo() {
      fail();
    }
  }

  @Nested
  class getEntities {

    @Disabled("Future work")
    @Test
    void todo() {
      fail();
    }
  }

  @Nested
  class getRelations {

    @Disabled("Future work")
    @Test
    void todo() {
      fail();
    }
  }

  @Nested
  class getInitialState {

    @Disabled("Future work")
    @Test
    void todo() {
      fail();
    }
  }

  @Nested
  class getFinalState {

    @Disabled("Future work")
    @Test
    void todo() {
      fail();
    }
  }

}
