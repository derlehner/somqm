package at.ac.tuwien.big.ame.somqm.server.dao;

import at.ac.tuwien.big.ame.somqm.server.dto.project.ProjectDetails;
import at.ac.tuwien.big.ame.somqm.server.dto.project.ProjectSummary;
import java.util.List;
import java.util.Optional;

public interface ProjectDaoCustom {

  /**
   * @param nameToSearch Optional.
   */
  List<ProjectSummary> findAll(String nameToSearch);

  Optional<ProjectDetails> findDetailsById(long id);
}
