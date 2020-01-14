package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.EntityInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.RelationInfo;
import java.util.Objects;

public class ActivityModelRelationInfo extends RelationInfo {

  private final String guard;

  public ActivityModelRelationInfo(EntityInfo source, EntityInfo target, String guard)
      throws IllegalArgumentException {
    super(source, target);
    this.guard = guard;
  }

  public String getGuard() {
    return guard;
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
    ActivityModelRelationInfo that = (ActivityModelRelationInfo) o;
    return Objects.equals(guard, that.guard);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), guard);
  }

  @Override
  public String toString() {
    return "ActivityModelRelationInfo{" +
        "source='" + getSource().getName() + '\'' +
        ", target='" + getTarget().getName() + '\'' +
        ", guard='" + guard + '\'' +
        '}';
  }

}
