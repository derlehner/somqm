package at.ac.tuwien.big.ame.somqm.server.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;
import at.ac.tuwien.big.ame.somqm.server.service.ServiceException;
import at.ac.tuwien.big.ame.somqm.server.service.UmlValidationService;
import at.ac.tuwien.big.ame.somqm.server.service.impl.UmlValidationServiceImpl;
import at.ac.tuwien.big.ame.somqm.server.util.TestHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.uml2.uml.Model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class UmlValidationServiceUT {

  private UmlValidationService validationService;

  @BeforeEach
  void setup() {
    validationService = new UmlValidationServiceImpl();
  }

  @Nested
  class load {

    @Nested
    class WithInvalidContent {

      @Test
      void emptyContentShouldFail() {
        // Arrange
        byte[] modelContent = new byte[0];

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
          validationService.load(modelContent);
        });
      }

      @Test
      void txtContentShouldFail() {
        // Arrange
        byte[] modelContent = TestHelper.getValidTxtContent();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
          validationService.load(modelContent);
        });
      }

      @Test
      void xmlContentShouldFail() {
        // Arrange
        byte[] modelContent = TestHelper.getValidXmlContent();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
          validationService.load(modelContent);
        });
      }

      @Test
      void invalidXmiContentShouldFail() {
        // Arrange
        byte[] modelContent = TestHelper.getInvalidXmiContent();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
          validationService.load(modelContent);
        });
      }

      @Test
      void xmiContentWithoutUmlModelElementShouldFail() {
        // Arrange
        byte[] modelContent = TestHelper.getXmiContentWithoutUmlModelElement();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
          validationService.load(modelContent);
        });
      }
    }

    @Nested
    class WithValidContent {

      @Test
      void classModel() {
        // Arrange
        byte[] modelContent = TestHelper.getValidClassModelContent();

        // Act
        Model model = validationService.load(modelContent);

        // Assert
        assertNotNull(model);
      }

      @Test
      void activityModel() {
        // Arrange
        byte[] modelContent = TestHelper.getValidActivityModelContent();

        // Act
        Model model = validationService.load(modelContent);

        // Assert
        assertNotNull(model);
      }

      @Test
      void stateMachineModel() {
        // Arrange
        byte[] modelContent = TestHelper.getValidStateMachineModelContent();

        // Act
        Model model = validationService.load(modelContent);

        // Assert
        assertNotNull(model);
      }
    }
  }

  @Nested
  class validate {

    @Nested
    class WithValidModel {

      @Test
      void classModel() throws ServiceException {
        // Arrange
        List<UmlModelType> supportedTypes = new ArrayList<>(Arrays.asList(UmlModelType.values()));
        byte[] modelContent = TestHelper.getValidClassModelContent();
        Model model = validationService.load(modelContent);

        // Act
        UmlModelType type = validationService.validate(model, supportedTypes);

        // Assert
        assertEquals(UmlModelType.CLASS_MODEL, type);
      }

      @Test
      void activityModel() throws ServiceException {
        // Arrange
        List<UmlModelType> supportedTypes = new ArrayList<>(Arrays.asList(UmlModelType.values()));
        byte[] modelContent = TestHelper.getValidActivityModelContent();
        Model model = validationService.load(modelContent);

        // Act
        UmlModelType type = validationService.validate(model, supportedTypes);

        // Assert
        assertEquals(UmlModelType.ACTIVITY_MODEL, type);
      }

      @Test
      void stateMachineModel() throws ServiceException {
        // Arrange
        List<UmlModelType> supportedTypes = new ArrayList<>(Arrays.asList(UmlModelType.values()));
        byte[] modelContent = TestHelper.getValidStateMachineModelContent();
        Model model = validationService.load(modelContent);

        // Act
        UmlModelType type = validationService.validate(model, supportedTypes);

        // Assert
        assertEquals(UmlModelType.STATE_MACHINE_MODEL, type);
      }
    }
  }

}
