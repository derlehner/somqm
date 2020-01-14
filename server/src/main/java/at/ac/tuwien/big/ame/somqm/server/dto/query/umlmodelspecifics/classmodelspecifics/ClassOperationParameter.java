package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics;

import java.util.Objects;

public class ClassOperationParameter {

  private final String name;
  private final ClassModelEntityInfo type;
  private final boolean optional;

  public ClassOperationParameter(String name, ClassModelEntityInfo type, boolean optional)
      throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException();
    }
    this.name = name;
    this.type = type;
    this.optional = optional;
  }

  public String getName() {
    return name;
  }

  public ClassModelEntityInfo getType() {
    return type;
  }

  public boolean isOptional() {
    return optional;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClassOperationParameter that = (ClassOperationParameter) o;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    String result = "ClassOperationParameter{";
    result += (optional ? "? " : "");
    result += name;
    result += ": " + (type == null ? "undefined" : type.getName());
    result += "}";
    return result;
  }

}
