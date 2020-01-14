package at.ac.tuwien.big.ame.somqm.server.dto.query.aggregation;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = false)
@JsonSubTypes(value = {
    @Type(value = CountQueryResultAggregation.class, name = "COUNT")
})
@ApiModel(discriminator = "type", subTypes = {
    CountQueryResultAggregation.class
})
public abstract class QueryResultAggregationType {

  @ApiModelProperty(required = true, allowableValues = "COUNT")
  private String type;

  QueryResultAggregationType() {
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
