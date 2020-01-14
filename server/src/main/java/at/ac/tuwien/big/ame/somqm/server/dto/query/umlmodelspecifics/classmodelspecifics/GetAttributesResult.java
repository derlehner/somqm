package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics;

import java.util.List;
import java.util.Objects;

public class GetAttributesResult {

  private final List<ClassAttribute> attributes;

  public GetAttributesResult(List<ClassAttribute> attributes) throws IllegalArgumentException {
    if (attributes == null) {
      throw new IllegalArgumentException();
    }
    this.attributes = attributes;
  }

  public List<ClassAttribute> getAttributes() {
    return attributes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetAttributesResult that = (GetAttributesResult) o;
    return attributes.equals(that.attributes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attributes);
  }

}
