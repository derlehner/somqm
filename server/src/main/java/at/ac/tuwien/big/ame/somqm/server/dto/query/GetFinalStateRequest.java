package at.ac.tuwien.big.ame.somqm.server.dto.query;

import io.swagger.annotations.ApiModelProperty;

public class GetFinalStateRequest {

  @ApiModelProperty(required = true)
  private long modelId;

  public GetFinalStateRequest() {
  }

  public long getModelId() {
    return modelId;
  }

  public void setModelId(long modelId) {
    this.modelId = modelId;
  }

  @Override
  public String toString() {
    return "GetFinalStateRequest{" +
        "modelId=" + modelId +
        '}';
  }
}
