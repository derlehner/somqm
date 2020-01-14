package at.ac.tuwien.big.ame.somqm.server.dto.query;

import io.swagger.annotations.ApiModelProperty;

public class GetInitialStateRequest {

  @ApiModelProperty(required = true)
  private long modelId;

  public GetInitialStateRequest() {
  }

  public long getModelId() {
    return modelId;
  }

  public void setModelId(long modelId) {
    this.modelId = modelId;
  }

  @Override
  public String toString() {
    return "GetInitialStateRequest{" +
        "modelId=" + modelId +
        '}';
  }
}
