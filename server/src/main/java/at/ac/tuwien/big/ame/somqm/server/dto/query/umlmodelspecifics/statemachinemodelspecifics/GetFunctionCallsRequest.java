package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.statemachinemodelspecifics;

import static at.ac.tuwien.big.ame.somqm.server.rest.QueryController.SWAGGER_REGEXFILTER_EXAMPLE;

import io.swagger.annotations.ApiModelProperty;

public class GetFunctionCallsRequest {

  @ApiModelProperty(required = true)
  private long projectId;

  @ApiModelProperty(required = false, notes = "If not set: Filter is ignored", example = SWAGGER_REGEXFILTER_EXAMPLE)
  private String functionCallNameRegex;

  public GetFunctionCallsRequest() {
  }

  public long getProjectId() {
    return projectId;
  }

  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }

  public String getFunctionCallNameRegex() {
    return functionCallNameRegex;
  }

  public void setFunctionCallNameRegex(String functionCallNameRegex) {
    this.functionCallNameRegex = functionCallNameRegex;
  }

  @Override
  public String toString() {
    return "GetFunctionCallsRequest{" +
        "projectId=" + projectId +
        ", functionCallNameRegex='" + functionCallNameRegex + '\'' +
        '}';
  }
}
