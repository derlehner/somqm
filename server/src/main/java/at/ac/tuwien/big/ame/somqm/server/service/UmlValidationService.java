package at.ac.tuwien.big.ame.somqm.server.service;

import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;
import java.util.List;
import java.util.Map;
import org.eclipse.uml2.uml.Model;

public interface UmlValidationService {

  /**
   * Load content as MDT-UML2 model.
   *
   * @return Never returns null.
   */
  Model load(byte[] modelContent) throws IllegalArgumentException;

  /**
   * Validates if the supplied model is valid according to the supplied supported types.
   *
   * @param supportedTypes Must not be empty.
   * @return Determined type of the validated model. Never returns null.
   */
  UmlModelType validate(Model model, List<UmlModelType> supportedTypes)
      throws IllegalArgumentException, ServiceException;

  /**
   * Verifies if the supplied model is consistent to the other supplied models (which can be of a
   * different type).
   */
  void verifyConsistency(Model model, UmlModelType type, Map<Model, UmlModelType> otherModels)
      throws IllegalArgumentException;
}
