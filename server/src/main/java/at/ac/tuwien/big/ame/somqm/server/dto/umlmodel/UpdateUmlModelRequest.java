package at.ac.tuwien.big.ame.somqm.server.dto.umlmodel;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UpdateUmlModelRequest {

  @NotBlank
  @ApiModelProperty(required = true)
  private String name;

  @NotNull
  @ApiModelProperty(required = true)
  private String description;

  public UpdateUmlModelRequest() {
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
    return "UpdateUmlModelRequest{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
