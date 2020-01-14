package at.ac.tuwien.big.ame.somqm.server.dto.query.umlmodelspecifics.activitymodelspecifics;

import at.ac.tuwien.big.ame.somqm.server.dto.query.aggregation.QueryResultAggregationType;

public class GetActivitiesAggregationRequest extends GetActivitiesRequest {

  private QueryResultAggregationType aggregationType;


  public QueryResultAggregationType getAggregationType() {
    return aggregationType;
  }

  public void setAggregationType(
      QueryResultAggregationType aggregationType) {
    this.aggregationType = aggregationType;
  }

  @Override
  public String toString() {
    return "GetActivitiesAggregationRequest{" +
        "aggregationType=" + aggregationType +
        '}';
  }
}
