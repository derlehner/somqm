package at.ac.tuwien.big.ame.somqm.server.dto.umlmodel;

import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;
import org.eclipse.uml2.uml.Model;

public class UmlModelUml2Content {

  private final Model model;
  private final UmlModelType type;

  public UmlModelUml2Content(Model model, UmlModelType type) throws IllegalArgumentException {
    if (model == null || type == null) {
      throw new IllegalArgumentException();
    }
    this.model = model;
    this.type = type;
  }

  public Model getModel() {
    return model;
  }

  public UmlModelType getType() {
    return type;
  }

  @Override
  public String toString() {
    return "UmlModelUml2Content{" +
        "model=" + "..." +
        ", type=" + type +
        '}';
  }
}
