package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.aggregation.QueryResultAggregationType;

public class GetNodesAggregationRequest extends GetNodesRequest {

  private QueryResultAggregationType aggregationType;

  public GetNodesAggregationRequest() {
  }

  public QueryResultAggregationType getAggregationType() {
    return aggregationType;
  }

  public void setAggregationType(
      QueryResultAggregationType aggregationType) {
    this.aggregationType = aggregationType;
  }

  @Override
  public String toString() {
    return "GetNodesAggregationRequest{" +
        "aggregationType=" + aggregationType +
        '}';
  }
}
