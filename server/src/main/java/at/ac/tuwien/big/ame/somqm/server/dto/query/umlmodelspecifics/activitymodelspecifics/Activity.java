package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package;

public class Activity extends ActivityModelEntityInfo {

  public Activity(String name, Package package_) throws IllegalArgumentException {
    super(name, package_);
  }

  @Override
  public String toString() {
    return "Activity{" +
        "fullName='" + getFullName() + '\'' +
        '}';
  }
}
