package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package;

public class FinalState extends State {

  public FinalState(String name, Package package_, FunctionCall entryFunctionCall,
      FunctionCall doActivityFunctionCall, FunctionCall exitFunctionCall) {
    super(name, package_, entryFunctionCall, doActivityFunctionCall, exitFunctionCall);
  }

  @Override
  public String toString() {
    return "FinalState{" +
        "fullName='" + getFullName() + '\'' +
        ", entryFunctionCall=" + getEntryFunctionCall() +
        ", doActivityFunctionCall=" + getDoActivityFunctionCall() +
        ", exitFunctionCall=" + getExitFunctionCall() +
        '}';
  }

}
