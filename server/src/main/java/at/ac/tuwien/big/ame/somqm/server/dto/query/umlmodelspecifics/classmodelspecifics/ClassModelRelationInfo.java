package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.EntityInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.RelationInfo;

public abstract class ClassModelRelationInfo extends RelationInfo {

  ClassModelRelationInfo(EntityInfo source, EntityInfo target) throws IllegalArgumentException {
    super(source, target);
  }

}
