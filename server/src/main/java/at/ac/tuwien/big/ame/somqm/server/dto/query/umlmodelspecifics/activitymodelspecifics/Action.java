package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.enumeration.ActionType;
import java.util.Objects;

public class Action extends ActivityModelEntityInfo {

  private final ActionType type;

  public Action(String name, Package package_, ActionType type) throws IllegalArgumentException {
    super(name, package_);
    if (type == null) {
      throw new IllegalArgumentException();
    }
    this.type = type;
  }

  public ActionType getType() {
    return type;
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
    Action action = (Action) o;
    return type == action.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), type);
  }

  @Override
  public String toString() {
    return "Action{" +
        "fullName='" + getFullName() + '\'' +
        ", type=" + type +
        '}';
  }
}
