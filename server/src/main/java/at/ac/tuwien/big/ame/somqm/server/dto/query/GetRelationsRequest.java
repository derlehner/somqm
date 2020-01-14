package at.ac.tuwien.big.ame.somqm.server.dto.query;

import static at.ac.tuwien.big.ame.somqm.server.rest.QueryController.SWAGGER_REGEXFILTER_EXAMPLE;

import io.swagger.annotations.ApiModelProperty;

public class GetRelationsRequest {

  @ApiModelProperty(required = true)
  private long modelId;

  @ApiModelProperty(required = true)
  private boolean includeTypeSpecificDetails;

  @ApiModelProperty(required = false, notes = "If not set: Filter is ignored", example = SWAGGER_REGEXFILTER_EXAMPLE)
  private String sourceEntityNameRegex;

  @ApiModelProperty(required = false, notes = "If not set: Filter is ignored", example = SWAGGER_REGEXFILTER_EXAMPLE)
  private String targetEntityNameRegex;

  public GetRelationsRequest() {

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

  public String getSourceEntityNameRegex() {
    return sourceEntityNameRegex;
  }

  public void setSourceEntityNameRegex(String sourceEntityNameRegex) {
    this.sourceEntityNameRegex = sourceEntityNameRegex;
  }

  public String getTargetEntityNameRegex() {
    return targetEntityNameRegex;
  }

  public void setTargetEntityNameRegex(String targetEntityNameRegex) {
    this.targetEntityNameRegex = targetEntityNameRegex;
  }

  @Override
  public String toString() {
    return "GetRelationsRequest{" +
        "modelId=" + modelId +
        ", includeTypeSpecificDetails=" + includeTypeSpecificDetails +
        ", sourceEntityNameRegex='" + sourceEntityNameRegex + '\'' +
        ", targetEntityNameRegex='" + targetEntityNameRegex + '\'' +
        '}';
  }
}
