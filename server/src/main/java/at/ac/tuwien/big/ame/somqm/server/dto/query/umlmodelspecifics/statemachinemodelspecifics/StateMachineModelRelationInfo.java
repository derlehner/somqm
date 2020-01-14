package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.EntityInfo;
import at.ac.tuwien.big.ame.somqm.server.dto.query.RelationInfo;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StateMachineModelRelationInfo extends RelationInfo {

  private final List<String> triggers;
  private final String guard;
  private final FunctionCall effect;

  public StateMachineModelRelationInfo(EntityInfo source, EntityInfo target, List<String> triggers,
      String guard, FunctionCall effect) throws IllegalArgumentException {
    super(source, target);
    if (triggers == null) {
      throw new IllegalArgumentException();
    }
    this.triggers = triggers;
    this.guard = guard;
    this.effect = effect;
  }

  public List<String> getTriggers() {
    return triggers;
  }

  public String getGuard() {
    return guard;
  }

  public FunctionCall getEffect() {
    return effect;
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
    StateMachineModelRelationInfo that = (StateMachineModelRelationInfo) o;
    return triggers.equals(that.triggers) &&
        Objects.equals(guard, that.guard) &&
        Objects.equals(effect, that.effect);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), triggers, guard, effect);
  }

  @Override
  public String toString() {
    return "StateMachineModelRelationInfo{" +
        "source='" + getSource().getName() + '\'' +
        ", target='" + getTarget().getName() + '\'' +
        ", triggers=[" + (triggers.isEmpty() ? ""
        : triggers.stream().map(Object::toString).collect(Collectors.joining(", "))) + "]" +
        ", guard='" + guard + '\'' +
        ", effect=" + effect +
        '}';
  }

}
