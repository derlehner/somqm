package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.EntityInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Modifier;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.enumeration.ClassType;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.enumeration.VisibilityType;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClassModelEntityInfo extends EntityInfo {

  private final ClassType type;
  private final List<ClassAttribute> attributes;
  private final List<ClassOperation> operations;
  private final List<Modifier> modifiers;
  private final VisibilityType visibility;

  public ClassModelEntityInfo(String name,
      Package package_,
      ClassType type,
      List<ClassAttribute> attributes,
      List<ClassOperation> operations,
      List<Modifier> modifiers,
      VisibilityType visibility) throws IllegalArgumentException {
    super(name, package_);
    if (type == null || attributes == null || operations == null || modifiers == null
        || visibility == null) {
      throw new IllegalArgumentException();
    }
    this.type = type;
    this.attributes = attributes;
    this.operations = operations;
    this.modifiers = modifiers;
    this.visibility = visibility;
  }

  public ClassType getType() {
    return type;
  }

  public List<ClassAttribute> getAttributes() {
    return attributes;
  }

  public List<ClassOperation> getOperations() {
    return operations;
  }

  public List<Modifier> getModifiers() {
    return modifiers;
  }

  public VisibilityType getVisibility() {
    return visibility;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), type, attributes, operations, modifiers, visibility);
  }

  @Override
  public String toString() {
    return "ClassModelEntityInfo{" +
        "fullName='" + getFullName() + '\'' +
        ", type=" + type +
        ", attributes=[" + (attributes.isEmpty() ? ""
        : attributes.stream().map(Object::toString).collect(Collectors.joining(", "))) + "]" +
        ", operations=[" + (operations.isEmpty() ? ""
        : operations.stream().map(Object::toString).collect(Collectors.joining(", "))) + "]" +
        ", modifiers=[" + (modifiers.isEmpty() ? ""
        : modifiers.stream().map(Modifier::getName).collect(Collectors.joining(", "))) + "]" +
        ", visibility=" + visibility +
        '}';
  }

}
