package at.ac.tuwien.big.ame.somqm.server.dto.query;

import java.util.List;

public class GetAllResult extends GetRelationsResult {

  public GetAllResult(List<RelationInfo> relations) throws IllegalArgumentException {
    super(relations);
  }

}
