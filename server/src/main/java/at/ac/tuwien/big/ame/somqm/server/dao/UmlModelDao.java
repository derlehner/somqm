package at.ac.tuwien.big.ame.somqm.server.dao;

import at.ac.tuwien.big.ame.somqm.server.model.UmlModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UmlModelDao extends JpaRepository<UmlModel, Long>, UmlModelDaoCustom {

  @Modifying
  @Query(value = "DELETE FROM UmlModel m WHERE m.project.id = :projectId")
  void deleteByProject(@Param("projectId") long projectId);

}
