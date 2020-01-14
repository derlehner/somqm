package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics;

import java.util.Objects;

public class Package {

  private final String name;
  private final Package superPackage;

  public Package(String name, Package superPackage) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException();
    }
    this.name = name;
    this.superPackage = superPackage;
  }

  public String getName() {
    return name;
  }

  public Package getSuperPackage() {
    return superPackage;
  }

  public String getFullName() {
    return superPackage == null ? name : superPackage.getFullName() + "." + name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Package aPackage = (Package) o;
    return name.equals(aPackage.name) &&
        Objects.equals(superPackage, aPackage.superPackage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, superPackage);
  }

  @Override
  public String toString() {
    return "Package{" +
        "fullName='" + getFullName() + '\'' +
        '}';
  }
}
