package at.ac.tuwien.big.ame.somqm.server.dto.query;

import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.ActivityModelEntityInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassModelEntityInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.StateMachineModelEntityInfo;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.base.CaseFormat;
import java.util.Objects;

public class EntityInfo {

  private final String name;
  private final Package package_;

  public EntityInfo(String name, Package package_) throws IllegalArgumentException {
    if (name == null || package_ == null) {
      throw new IllegalArgumentException();
    }
    this.name = name;
    this.package_ = package_;
  }

  @JsonGetter("entityType")
  @JsonInclude(Include.NON_NULL)
  private String getEntityType() {
    String entityType = null;
    if (this instanceof ClassModelEntityInfo) {
      entityType = "Class";
    } else if (this instanceof ActivityModelEntityInfo
        || this instanceof StateMachineModelEntityInfo) {
      entityType = getClass().getSimpleName();
    }
    if (entityType != null) {
      entityType = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, entityType);
    }
    return entityType;
  }

  public String getName() {
    return name;
  }

  public String getFullName() {
    String packageName = package_.getFullName();
    return packageName.isEmpty() ? name : (packageName + "." + name);
  }

  public Package getPackage() {
    return package_;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EntityInfo that = (EntityInfo) o;
    return name.equals(that.name) &&
        package_.equals(that.package_);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, package_);
  }

  @Override
  public String toString() {
    return "EntityInfo{" +
        "fullName='" + getFullName() + '\'' +
        '}';
  }
}
