package at.ac.tuwien.big.ame.somqm.server.dao;

import at.ac.tuwien.big.ame.somqm.server.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDao extends JpaRepository<Project, Long>, ProjectDaoCustom {

  /**
   * @return Corresponding project. Null if not found.
   */
  Project findByName(String name);

  boolean existsByName(String name);

}
