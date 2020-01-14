package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package;
import java.util.Objects;

public class State extends StateMachineModelEntityInfo {

  private final FunctionCall entryFunctionCall;
  private final FunctionCall doActivityFunctionCall;
  private final FunctionCall exitFunctionCall;

  public State(String name, Package package_, FunctionCall entryFunctionCall,
      FunctionCall doActivityFunctionCall, FunctionCall exitFunctionCall) {
    super(name, package_);
    this.entryFunctionCall = entryFunctionCall;
    this.doActivityFunctionCall = doActivityFunctionCall;
    this.exitFunctionCall = exitFunctionCall;
  }

  public FunctionCall getEntryFunctionCall() {
    return entryFunctionCall;
  }

  public FunctionCall getDoActivityFunctionCall() {
    return doActivityFunctionCall;
  }

  public FunctionCall getExitFunctionCall() {
    return exitFunctionCall;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    State state = (State) o;
    return Objects.equals(entryFunctionCall, state.entryFunctionCall) &&
        Objects.equals(doActivityFunctionCall, state.doActivityFunctionCall) &&
        Objects.equals(exitFunctionCall, state.exitFunctionCall);
  }

  @Override
  public int hashCode() {
    return Objects
        .hash(super.hashCode(), entryFunctionCall, doActivityFunctionCall, exitFunctionCall);
  }

  @Override
  public String toString() {
    return "State{" +
        "fullName='" + getFullName() + '\'' +
        ", entryFunctionCall=" + entryFunctionCall +
        ", doActivityFunctionCall=" + doActivityFunctionCall +
        ", exitFunctionCall=" + exitFunctionCall +
        '}';
  }

}
