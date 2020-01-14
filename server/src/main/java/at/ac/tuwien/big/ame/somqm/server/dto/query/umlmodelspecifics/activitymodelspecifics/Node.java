package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.Package;
import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.enumeration.NodeType;
import java.util.Objects;

public class Node extends ActivityModelEntityInfo {

  private final NodeType type;

  public Node(String name, Package package_, NodeType type)
      throws IllegalArgumentException {
    super(name, package_);
    if (type == null) {
      throw new IllegalArgumentException();
    }
    this.type = type;
  }

  public NodeType getType() {
    return type;
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
    Node node = (Node) o;
    return type == node.type;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), type);
  }

  @Override
  public String toString() {
    return "Node{" +
        "fullName='" + getFullName() + '\'' +
        ", type=" + type +
        '}';
  }
}
