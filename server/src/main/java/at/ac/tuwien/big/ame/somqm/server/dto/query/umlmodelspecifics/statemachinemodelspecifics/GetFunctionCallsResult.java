package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics;

import java.util.List;
import java.util.Objects;

public class GetFunctionCallsResult {

  private final List<FunctionCall> functionCalls;

  public GetFunctionCallsResult(List<FunctionCall> functionCalls)
      throws IllegalArgumentException {
    if (functionCalls == null) {
      throw new IllegalArgumentException();
    }
    this.functionCalls = functionCalls;
  }

  public List<FunctionCall> getFunctionCalls() {
    return functionCalls;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetFunctionCallsResult that = (GetFunctionCallsResult) o;
    return functionCalls.equals(that.functionCalls);
  }

  @Override
  public int hashCode() {
    return Objects.hash(functionCalls);
  }

}
