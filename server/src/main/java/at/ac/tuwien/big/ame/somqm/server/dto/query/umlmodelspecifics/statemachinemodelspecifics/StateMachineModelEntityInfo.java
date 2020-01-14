package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.EntityInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package;

public abstract class StateMachineModelEntityInfo extends EntityInfo {

  StateMachineModelEntityInfo(String name, Package package_) throws IllegalArgumentException {
    super(name, package_);
  }

}
