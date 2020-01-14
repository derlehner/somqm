package at.ac.tuwien.big.ame.somqm.server.dto.query;

import static at.ac.tuwien.big.ame.somqm.server.rest.QueryController.SWAGGER_REGEXFILTER_EXAMPLE;

import io.swagger.annotations.ApiModelProperty;

public class GetEntitiesRequest {

  @ApiModelProperty(required = true)
  private long modelId;

  @ApiModelProperty(required = true)
  private boolean includeTypeSpecificDetails;

  @ApiModelProperty(required = false, notes = "If not set: Filter is ignored", example = SWAGGER_REGEXFILTER_EXAMPLE)
  private String entityNameRegex;

  @ApiModelProperty(required = false, notes = "If not set: Filter is ignored", example = SWAGGER_REGEXFILTER_EXAMPLE)
  private String packageNameRegex;

  public GetEntitiesRequest() {
  }

  public long getModelId() {
    return modelId;
  }

  public void setModelId(long modelId) {
    this.modelId = modelId;
  }

  public boolean isIncludeTypeSpecificDetails() {
    return includeTypeSpecificDetails;
  }

  public void setIncludeTypeSpecificDetails(boolean includeTypeSpecificDetails) {
    this.includeTypeSpecificDetails = includeTypeSpecificDetails;
  }

  public String getEntityNameRegex() {
    return entityNameRegex;
  }

  public void setEntityNameRegex(String entityNameRegex) {
    this.entityNameRegex = entityNameRegex;
  }

  public String getPackageNameRegex() {
    return packageNameRegex;
  }

  public void setPackageNameRegex(String packageNameRegex) {
    this.packageNameRegex = packageNameRegex;
  }

  @Override
  public String toString() {
    return "GetEntitiesRequest{" +
        "modelId=" + modelId +
        ", includeTypeSpecificDetails=" + includeTypeSpecificDetails +
        ", entityNameRegex='" + entityNameRegex + '\'' +
        ", packageNameRegex='" + packageNameRegex + '\'' +
        '}';
  }
}
