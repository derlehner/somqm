package at.ac.tuwien.big.ame.somqm.server.dto.query;

import at.ac.tuwien.big.ame.somqm.server.dto.query.aggregation.QueryResultAggregationType;

public class GetAllAggregationRequest extends GetAllRequest {

  private QueryResultAggregationType aggregationType;

  public GetAllAggregationRequest() {
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
    return "GetAllAggregationRequest{" +
        "aggregationType=" + aggregationType +
        '}';
  }
}
