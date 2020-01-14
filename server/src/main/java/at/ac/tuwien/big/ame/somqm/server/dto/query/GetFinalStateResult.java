package at.ac.tuwien.big.ame.somqm.server.dto.query;

import java.util.Objects;

public class GetFinalStateResult {

  private final EntityInfo finalState;

  public GetFinalStateResult(EntityInfo finalState) throws IllegalArgumentException {
    if (finalState == null) {
      throw new IllegalArgumentException();
    }
    this.finalState = finalState;
  }

  public EntityInfo getFinalState() {
    return finalState;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetFinalStateResult that = (GetFinalStateResult) o;
    return finalState.equals(that.finalState);
  }

  @Override
  public int hashCode() {
    return Objects.hash(finalState);
  }
}
