package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Modifier;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.enumeration.VisibilityType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClassAttribute {

  private final String name;
  private final ClassModelEntityInfo type;
  private final VisibilityType visibility;
  private final List<Modifier> modifiers;

  public ClassAttribute(String name,
      ClassModelEntityInfo type,
      VisibilityType visibility,
      List<Modifier> modifiers) throws IllegalArgumentException {
    if (name == null || visibility == null || modifiers == null) {
      throw new IllegalArgumentException();
    }
    this.name = name;
    this.type = type;
    this.visibility = visibility;
    this.modifiers = modifiers;
  }

  public String getName() {
    return name;
  }

  public ClassModelEntityInfo getType() {
    return type;
  }

  public VisibilityType getVisibility() {
    return visibility;
  }

  public List<Modifier> getModifiers() {
    return modifiers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClassAttribute that = (ClassAttribute) o;
    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    String result = "ClassAttribute{";
    result += visibility + "";
    result += (modifiers.isEmpty() ? ""
        : " " + modifiers.stream().map(Modifier::getName).collect(Collectors.joining(" ")));
    result += " " + name;
    result += ": " + (type == null ? "undefined" : type.getName());
    result += "}";
    return result;
  }
}
