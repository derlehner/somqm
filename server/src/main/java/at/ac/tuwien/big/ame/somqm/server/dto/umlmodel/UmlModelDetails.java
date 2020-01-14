package at.ac.tuwien.big.ame.somqm.server.dto.umlmodel;

import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;
import java.time.LocalDateTime;

public class UmlModelDetails {

  private long id;
  private String name;
  private String description;
  private LocalDateTime createdOn;
  private LocalDateTime modifiedOn;
  private UmlModelType type;
  private long projectId;

  public UmlModelDetails() {

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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(LocalDateTime createdOn) {
    this.createdOn = createdOn;
  }

  public LocalDateTime getModifiedOn() {
    return modifiedOn;
  }

  public void setModifiedOn(LocalDateTime modifiedOn) {
    this.modifiedOn = modifiedOn;
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
    return "UmlModelDetails{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", createdOn=" + createdOn +
        ", modifiedOn=" + modifiedOn +
        ", type=" + type +
        ", projectId=" + projectId +
        '}';
  }
}
