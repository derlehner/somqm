package at.ac.tuwien.big.ame.somqm.server.dto.umlmodel;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateUmlModelRequest {

  @NotBlank
  @ApiModelProperty(required = true)
  private String name;

  @NotNull
  @ApiModelProperty(required = true)
  private String description;

  @NotNull
  @ApiModelProperty(required = true)
  private long projectId;

  public CreateUmlModelRequest() {
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

  public long getProjectId() {
    return projectId;
  }

  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }

  @Override
  public String toString() {
    return "CreateUmlModelRequest{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", projectId=" + projectId +
        '}';
  }
}
