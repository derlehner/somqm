package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics;

import static at.ac.tuwien.big.ame.somqm.server.rest.QueryController.SWAGGER_REGEXFILTER_EXAMPLE;

import io.swagger.annotations.ApiModelProperty;

public class GetActionsRequest {

  @ApiModelProperty(required = true)
  private long projectId;

  @ApiModelProperty(required = false, notes = "If not set: Filter is ignored", example = SWAGGER_REGEXFILTER_EXAMPLE)
  private String actionNameRegex;

  @ApiModelProperty(required = false, notes = "If not set: Filter is ignored", example = SWAGGER_REGEXFILTER_EXAMPLE)
  private String activityNameRegex;

  @ApiModelProperty(required = false, notes = "If not set: Filter is ignored", example = SWAGGER_REGEXFILTER_EXAMPLE)
  private String packageNameRegex;

  public GetActionsRequest() {
  }

  public long getProjectId() {
    return projectId;
  }

  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }

  public String getActionNameRegex() {
    return actionNameRegex;
  }

  public void setActionNameRegex(String actionNameRegex) {
    this.actionNameRegex = actionNameRegex;
  }

  public String getActivityNameRegex() {
    return activityNameRegex;
  }

  public void setActivityNameRegex(String activityNameRegex) {
    this.activityNameRegex = activityNameRegex;
  }

  public String getPackageNameRegex() {
    return packageNameRegex;
  }

  public void setPackageNameRegex(String packageNameRegex) {
    this.packageNameRegex = packageNameRegex;
  }

  @Override
  public String toString() {
    return "GetActionsRequest{" +
        "projectId=" + projectId +
        ", actionNameRegex='" + actionNameRegex + '\'' +
        ", activityNameRegex='" + activityNameRegex + '\'' +
        ", packageNameRegex='" + packageNameRegex + '\'' +
        '}';
  }
}
