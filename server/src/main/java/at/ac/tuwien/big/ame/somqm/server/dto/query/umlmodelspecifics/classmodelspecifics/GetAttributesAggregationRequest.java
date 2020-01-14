package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.classmodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.aggregation.QueryResultAggregationType;

public class GetAttributesAggregationRequest extends GetAttributesRequest {

  private QueryResultAggregationType aggregationType;

  public GetAttributesAggregationRequest() {
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
    return "GetAttributesAggregationRequest{" +
        "aggregationType=" + aggregationType +
        '}';
  }
}
