package at.ac.tuwien.big.ame.somqm.server.dto.project;

import java.time.LocalDateTime;

public class ProjectDetails {

  private long id;
  private String name;
  private String description;
  private LocalDateTime createdOn;
  private LocalDateTime modifiedOn;

  public ProjectDetails() {

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

  @Override
  public String toString() {
    return "ProjectDetails{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", createdOn=" + createdOn +
        ", modifiedOn=" + modifiedOn +
        '}';
  }
}
