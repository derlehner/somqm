package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics;

import static at.ac.tuwien.big.ame.somqm.server.rest.QueryController.SWAGGER_REGEXFILTER_EXAMPLE;

import at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics.enumeration.NodeType;
import io.swagger.annotations.ApiModelProperty;

public class GetNodesRequest {

  @ApiModelProperty(required = true)
  private long projectId;

  @ApiModelProperty(required = false, notes = "If not set: Filter is ignored", example = SWAGGER_REGEXFILTER_EXAMPLE)
  private String nodeNameRegex;

  @ApiModelProperty(required = false, notes = "If not set: Filter is ignored", example = SWAGGER_REGEXFILTER_EXAMPLE)
  private String activityNameRegex;

  @ApiModelProperty(required = false, notes = "If not set: Filter is ignored", example = SWAGGER_REGEXFILTER_EXAMPLE)
  private String packageNameRegex;

  @ApiModelProperty(required = false, notes = "If not set: Filter is ignored")
  private NodeType nodeType;

  public GetNodesRequest() {
  }

  public long getProjectId() {
    return projectId;
  }

  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }

  public String getNodeNameRegex() {
    return nodeNameRegex;
  }

  public void setNodeNameRegex(String nodeNameRegex) {
    this.nodeNameRegex = nodeNameRegex;
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

  public NodeType getNodeType() {
    return nodeType;
  }

  public void setNodeType(
      NodeType nodeType) {
    this.nodeType = nodeType;
  }

  @Override
  public String toString() {
    return "GetNodesRequest{" +
        "projectId=" + projectId +
        ", nodeNameRegex='" + nodeNameRegex + '\'' +
        ", activityNameRegex='" + activityNameRegex + '\'' +
        ", packageNameRegex='" + packageNameRegex + '\'' +
        ", nodeType=" + nodeType +
        '}';
  }
}
