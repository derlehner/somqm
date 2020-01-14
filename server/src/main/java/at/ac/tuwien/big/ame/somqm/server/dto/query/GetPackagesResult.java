package at.ac.tuwien.big.ame.somqm.server.dto.query;

import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package;
import java.util.List;
import java.util.Objects;

public class GetPackagesResult {

  private final List<Package> packages;

  public GetPackagesResult(List<Package> packages) throws IllegalArgumentException {
    if (packages == null) {
      throw new IllegalArgumentException();
    }
    this.packages = packages;
  }

  public List<Package> getPackages() {
    return packages;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetPackagesResult that = (GetPackagesResult) o;
    return packages.equals(that.packages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(packages);
  }

}
