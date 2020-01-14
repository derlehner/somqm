package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.EntityInfo;

public class ClassModelInterfaceRealizationRelationInfo extends ClassModelRelationInfo {

  public ClassModelInterfaceRealizationRelationInfo(EntityInfo source, EntityInfo target)
      throws IllegalArgumentException {
    super(source, target);
  }

  @Override
  public String toString() {
    return "ClassModelInterfaceRealizationRelationInfo{" +
        "source='" + getSource().getName() + '\'' +
        ", target='" + getTarget().getName() + '\'' +
        '}';
  }
}
