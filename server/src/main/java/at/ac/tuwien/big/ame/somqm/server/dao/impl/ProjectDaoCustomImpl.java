package at.ac.tuwien.big.ame.somqm.server.dao.impl;

import at.ac.tuwien.big.ame.somqm.server.dao.ProjectDaoCustom;
import at.ac.tuwien.big.ame.somqm.server.dto.project.ProjectDetails;
import at.ac.tuwien.big.ame.somqm.server.dto.project.ProjectSummary;
import at.ac.tuwien.big.ame.somqm.server.model.Project;
import at.ac.tuwien.big.ame.somqm.server.model.Project_;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProjectDaoCustomImpl implements ProjectDaoCustom {

  private final EntityManager em;

  public ProjectDaoCustomImpl(EntityManager em) {
    this.em = em;
  }

  @Override
  public List<ProjectSummary> findAll(String nameToSearch) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Tuple> cq = cb.createTupleQuery();
    Root<Project> projectRoot = cq.from(Project.class);

    List<Predicate> conditions = new ArrayList<>();
    if (nameToSearch != null) {
      conditions.add(cb.like(cb.lower(projectRoot.get(Project_.name)),
          "%" + nameToSearch.toLowerCase() + "%"));
    }

    TypedQuery<Tuple> typedQuery = em.createQuery(cq
        .select(cb.tuple(
            projectRoot.get(Project_.id),
            projectRoot.get(Project_.name)))
        .where(conditions.toArray(new Predicate[]{}))
        .orderBy(cb.asc(projectRoot.get(Project_.id))));
    List<Tuple> result = typedQuery.getResultList();

    List<ProjectSummary> projectSummaries = new ArrayList<>();
    for (Tuple t : result) {
      long id = t.get(0, Long.class);
      String name = t.get(1, String.class);
      ProjectSummary projectSummary = new ProjectSummary();
      projectSummary.setId(id);
      projectSummary.setName(name);
      projectSummaries.add(projectSummary);
    }
    return projectSummaries;
  }

  @Override
  public Optional<ProjectDetails> findDetailsById(long id) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Tuple> cq = cb.createTupleQuery();
    Root<Project> projectRoot = cq.from(Project.class);

    List<Predicate> conditions = new ArrayList<>();
    conditions.add(cb.equal(projectRoot.get(Project_.id), id));

    TypedQuery<Tuple> typedQuery = em.createQuery(cq
        .select(cb.tuple(
            projectRoot.get(Project_.name),
            projectRoot.get(Project_.description),
            projectRoot.get(Project_.createdOn),
            projectRoot.get(Project_.modifiedOn)))
        .where(conditions.toArray(new Predicate[]{})));
    Tuple t;
    try {
      t = typedQuery.getSingleResult();
    } catch (NoResultException e) {
      return Optional.empty();
    }

    String name = t.get(0, String.class);
    String description = t.get(1, String.class);
    LocalDateTime createdOn = t.get(2, LocalDateTime.class);
    LocalDateTime modifiedOn = t.get(3, LocalDateTime.class);
    ProjectDetails projectDetails = new ProjectDetails();
    projectDetails.setId(id);
    projectDetails.setName(name);
    projectDetails.setDescription(description);
    projectDetails.setCreatedOn(createdOn);
    projectDetails.setModifiedOn(modifiedOn);
    return Optional.of(projectDetails);
  }
}
