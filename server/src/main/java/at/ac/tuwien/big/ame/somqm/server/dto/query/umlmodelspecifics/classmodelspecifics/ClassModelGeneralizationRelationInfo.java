package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.EntityInfo;

public class ClassModelGeneralizationRelationInfo extends ClassModelRelationInfo {

  public ClassModelGeneralizationRelationInfo(EntityInfo source, EntityInfo target)
      throws IllegalArgumentException {
    super(source, target);
  }

  @Override
  public String toString() {
    return "ClassModelGeneralizationRelationInfo{" +
        "source='" + getSource().getName() + '\'' +
        ", target='" + getTarget().getName() + '\'' +
        '}';
  }
}
