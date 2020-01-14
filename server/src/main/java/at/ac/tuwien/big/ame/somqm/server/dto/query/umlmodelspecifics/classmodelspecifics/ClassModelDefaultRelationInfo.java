package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.EntityInfo;
import java.util.Objects;

public class ClassModelDefaultRelationInfo extends ClassModelRelationInfo {

  private final String name;
  private final String sourceMultiplicity;
  private final boolean isSourceNavigable;
  private final String targetMultiplicity;
  private final boolean isTargetNavigable;

  public ClassModelDefaultRelationInfo(EntityInfo source, EntityInfo target, String name,
      String sourceMultiplicity, boolean isSourceNavigable, String targetMultiplicity,
      boolean isTargetNavigable) throws IllegalArgumentException {
    super(source, target);
    if (name == null || sourceMultiplicity == null || targetMultiplicity == null) {
      throw new IllegalArgumentException();
    }
    this.name = name;
    this.sourceMultiplicity = sourceMultiplicity;
    this.isSourceNavigable = isSourceNavigable;
    this.targetMultiplicity = targetMultiplicity;
    this.isTargetNavigable = isTargetNavigable;
  }

  public String getName() {
    return name;
  }

  public String getSourceMultiplicity() {
    return sourceMultiplicity;
  }

  public boolean isSourceNavigable() {
    return isSourceNavigable;
  }

  public String getTargetMultiplicity() {
    return targetMultiplicity;
  }

  public boolean isTargetNavigable() {
    return isTargetNavigable;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    ClassModelDefaultRelationInfo that = (ClassModelDefaultRelationInfo) o;
    return isSourceNavigable == that.isSourceNavigable &&
        isTargetNavigable == that.isTargetNavigable &&
        name.equals(that.name) &&
        sourceMultiplicity.equals(that.sourceMultiplicity) &&
        targetMultiplicity.equals(that.targetMultiplicity);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(super.hashCode(), name, sourceMultiplicity, isSourceNavigable, targetMultiplicity,
            isTargetNavigable);
  }

  @Override
  public String toString() {
    return "ClassModelDefaultRelationInfo{" +
        "source='" + getSource().getName() + '\'' +
        ", target='" + getTarget().getName() + '\'' +
        ", name='" + name + '\'' +
        ", sourceMultiplicity='" + sourceMultiplicity + '\'' +
        ", isSourceNavigable=" + isSourceNavigable +
        ", targetMultiplicity='" + targetMultiplicity + '\'' +
        ", isTargetNavigable=" + isTargetNavigable +
        '}';
  }

}
