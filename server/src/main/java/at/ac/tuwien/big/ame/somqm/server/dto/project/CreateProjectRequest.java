package at.ac.tuwien.big.ame.somqm.server.dto.project;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateProjectRequest {

  @NotBlank
  @ApiModelProperty(required = true, notes = "Must be unique across all projects")
  private String name;

  @NotNull
  @ApiModelProperty(required = true)
  private String description;

  public CreateProjectRequest() {
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

  @Override
  public String toString() {
    return "CreateProjectRequest{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
