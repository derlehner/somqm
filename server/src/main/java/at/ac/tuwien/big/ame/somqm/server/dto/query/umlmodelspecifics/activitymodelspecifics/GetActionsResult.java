package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics;

import java.util.List;
import java.util.Objects;

public class GetActionsResult {

  private final List<Action> actions;

  public GetActionsResult(List<Action> actions) throws IllegalArgumentException {
    if (actions == null) {
      throw new IllegalArgumentException();
    }
    this.actions = actions;
  }

  public List<Action> getActions() {
    return actions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetActionsResult that = (GetActionsResult) o;
    return actions.equals(that.actions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(actions);
  }
}
