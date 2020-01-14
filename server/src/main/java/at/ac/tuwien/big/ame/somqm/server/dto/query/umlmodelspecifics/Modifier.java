package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics;

import java.util.Objects;

public class Modifier {

  private final String name;

  public Modifier(String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException();
    }
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Modifier modifier = (Modifier) o;
    return name.equals(modifier.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "Modifier{" +
        "name='" + name + '\'' +
        '}';
  }
}
