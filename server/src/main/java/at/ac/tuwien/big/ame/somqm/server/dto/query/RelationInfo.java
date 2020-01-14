package at.ac.tuwien.big.ame.somqm.server.dto.query;

import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.ActivityModelRelationInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics.ClassModelRelationInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics.StateMachineModelRelationInfo;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.base.CaseFormat;
import java.util.Objects;

public class RelationInfo {

  private final EntityInfo source;
  private final EntityInfo target;

  public RelationInfo(EntityInfo source, EntityInfo target) throws IllegalArgumentException {
    if (source == null || target == null) {
      throw new IllegalArgumentException();
    }
    this.source = source;
    this.target = target;
  }

  @JsonGetter("relationType")
  @JsonInclude(Include.NON_NULL)
  private String getRelationType() {
    String relationType = null;
    if (this instanceof ClassModelRelationInfo) {
      relationType = getClass().getSimpleName().replace("ClassModel", "")
          .replace("RelationInfo", "");
    } else if (this instanceof ActivityModelRelationInfo) {
      relationType = "Flow";
    } else if (this instanceof StateMachineModelRelationInfo) {
      relationType = "Transition";
    }
    if (relationType != null) {
      relationType = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, relationType);
    }
    return relationType;
  }

  public EntityInfo getSource() {
    return source;
  }

  public EntityInfo getTarget() {
    return target;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RelationInfo that = (RelationInfo) o;
    return source.equals(that.source) &&
        target.equals(that.target);
  }

  @Override
  public int hashCode() {
    return Objects.hash(source, target);
  }

  @Override
  public String toString() {
    return "RelationInfo{" +
        "source='" + source.getName() + '\'' +
        ", target='" + target.getName() + '\'' +
        '}';
  }
}
