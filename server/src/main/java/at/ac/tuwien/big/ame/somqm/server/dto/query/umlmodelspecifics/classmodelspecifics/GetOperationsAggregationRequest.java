package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.aggregation.QueryResultAggregationType;

public class GetOperationsAggregationRequest extends GetOperationsRequest {

  private QueryResultAggregationType aggregationType;

  public GetOperationsAggregationRequest() {
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
    return "GetOperationsAggregationRequest{" +
        "aggregationType=" + aggregationType +
        '}';
  }
}
