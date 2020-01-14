package at.ac.tuwien.big.ame.somqm.server.dto.umlmodel;

import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;

public class UmlModelSummary {

  private long id;
  private String name;
  private UmlModelType type;
  private long projectId;

  public UmlModelSummary() {

  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UmlModelType getType() {
    return type;
  }

  public void setType(UmlModelType type) {
    this.type = type;
  }

  public long getProjectId() {
    return projectId;
  }

  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }

  @Override
  public String toString() {
    return "UmlModelSummary{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", type=" + type +
        ", projectId=" + projectId +
        '}';
  }
}
