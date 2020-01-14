package at.ac.tuwien.big.ame.somqm.server.dto.query;

import static at.ac.tuwien.big.ame.somqm.server.rest.QueryController.SWAGGER_REGEXFILTER_EXAMPLE;

import io.swagger.annotations.ApiModelProperty;

public class GetPackagesRequest {

  @ApiModelProperty(required = true)
  private long modelId;

  @ApiModelProperty(required = false, notes = "If not set: Filter is ignored", example = SWAGGER_REGEXFILTER_EXAMPLE)
  private String packageNameRegex;

  public GetPackagesRequest() {
  }

  public long getModelId() {
    return modelId;
  }

  public void setModelId(long modelId) {
    this.modelId = modelId;
  }

  public String getPackageNameRegex() {
    return packageNameRegex;
  }

  public void setPackageNameRegex(String packageNameRegex) {
    this.packageNameRegex = packageNameRegex;
  }

  @Override
  public String toString() {
    return "GetPackagesRequest{" +
        "modelId=" + modelId +
        ", packageNameRegex='" + packageNameRegex + '\'' +
        '}';
  }
}
