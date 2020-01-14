package at.ac.tuwien.big.ame.somqm.server.dto.query;

import at.ac.tuwien.big.ame.somqm.server.dto.query.aggregation.QueryResultAggregationType;

public class GetEntitiesAggregationRequest extends GetEntitiesRequest {

  private QueryResultAggregationType aggregationType;

  public GetEntitiesAggregationRequest() {
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
    return "GetEntitiesAggregationRequest{" +
        "aggregationType=" + aggregationType +
        '}';
  }
}
