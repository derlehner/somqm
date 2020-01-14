package at.ac.tuwien.big.ame.somqm.server.dao;

import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelDetails;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelSummary;
import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;

public interface UmlModelDaoCustom {

  /**
   * @param nameToSearch Optional.
   * @param typeToSearch Optional.
   * @param projectIdToSearch Optional.
   */
  List<UmlModelSummary> findAll(String nameToSearch, UmlModelType typeToSearch,
      Long projectIdToSearch);

  Optional<UmlModelDetails> findDetailsById(long id);

  /**
   * @param excludedUmlModelId Optional.
   */
  Map<byte[], UmlModelType> findContentByProject(long projectId, Long excludedUmlModelId);

  OptionalLong findIdByTypeAndProject(UmlModelType type, long projectId);

}
