package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics;

import java.util.List;
import java.util.Objects;

public class GetNodesResult {

  private final List<Node> nodes;

  public GetNodesResult(List<Node> nodes) throws IllegalArgumentException {
    if (nodes == null) {
      throw new IllegalArgumentException();
    }
    this.nodes = nodes;
  }

  public List<Node> getNodes() {
    return nodes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetNodesResult that = (GetNodesResult) o;
    return nodes.equals(that.nodes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nodes);
  }

}
