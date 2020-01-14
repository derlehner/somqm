package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics;

import static at.ac.tuwien.big.ame.somqm.server.rest.QueryController.SWAGGER_REGEXFILTER_EXAMPLE;

import io.swagger.annotations.ApiModelProperty;

public class GetOperationsRequest {

  @ApiModelProperty(required = true)
  private long projectId;

  @ApiModelProperty(required = false, notes = "If not set: Filter is ignored", example = SWAGGER_REGEXFILTER_EXAMPLE)
  private String operationNameRegex;

  @ApiModelProperty(required = false, notes = "If not set: Filter is ignored", example = SWAGGER_REGEXFILTER_EXAMPLE)
  private String entityNameRegex;

  public GetOperationsRequest() {
  }

  public long getProjectId() {
    return projectId;
  }

  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }

  public String getOperationNameRegex() {
    return operationNameRegex;
  }

  public void setOperationNameRegex(String operationNameRegex) {
    this.operationNameRegex = operationNameRegex;
  }

  public String getEntityNameRegex() {
    return entityNameRegex;
  }

  public void setEntityNameRegex(String entityNameRegex) {
    this.entityNameRegex = entityNameRegex;
  }

  @Override
  public String toString() {
    return "GetOperationsRequest{" +
        "projectId=" + projectId +
        ", operationNameRegex='" + operationNameRegex + '\'' +
        ", entityNameRegex='" + entityNameRegex + '\'' +
        '}';
  }
}
