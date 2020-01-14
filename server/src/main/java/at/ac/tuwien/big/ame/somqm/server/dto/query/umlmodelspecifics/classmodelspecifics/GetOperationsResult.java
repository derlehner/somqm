package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics;

import java.util.List;
import java.util.Objects;

public class GetOperationsResult {

  private final List<ClassOperation> operations;

  public GetOperationsResult(List<ClassOperation> operations) throws IllegalArgumentException {
    if (operations == null) {
      throw new IllegalArgumentException();
    }
    this.operations = operations;
  }

  public List<ClassOperation> getOperations() {
    return operations;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetOperationsResult that = (GetOperationsResult) o;
    return operations.equals(that.operations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(operations);
  }

}
