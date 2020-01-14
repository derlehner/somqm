package at.ac.tuwien.big.ame.somqm.server.dto.query;

import java.util.Objects;

public class GetInitialStateResult {

  private final EntityInfo initialState;

  public GetInitialStateResult(EntityInfo initialState) throws IllegalArgumentException {
    if (initialState == null) {
      throw new IllegalArgumentException();
    }
    this.initialState = initialState;
  }

  public EntityInfo getInitialState() {
    return initialState;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetInitialStateResult that = (GetInitialStateResult) o;
    return initialState.equals(that.initialState);
  }

  @Override
  public int hashCode() {
    return Objects.hash(initialState);
  }
}
