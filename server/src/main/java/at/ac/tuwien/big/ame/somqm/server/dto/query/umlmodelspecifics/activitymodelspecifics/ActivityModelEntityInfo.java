package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.EntityInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package;

public abstract class ActivityModelEntityInfo extends EntityInfo {

  ActivityModelEntityInfo(String name, Package package_) throws IllegalArgumentException {
    super(name, package_);
  }

}
