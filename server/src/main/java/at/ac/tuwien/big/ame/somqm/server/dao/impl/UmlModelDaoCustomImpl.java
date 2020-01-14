package at.ac.tuwien.big.ame.somqm.server.dao.impl;

import at.ac.tuwien.big.ame.somqm.server.dao.UmlModelDaoCustom;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelDetails;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelSummary;
import at.ac.tuwien.big.ame.somqm.server.model.Project;
import at.ac.tuwien.big.ame.somqm.server.model.Project_;
import at.ac.tuwien.big.ame.somqm.server.model.UmlModel;
import at.ac.tuwien.big.ame.somqm.server.model.UmlModel_;
import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalLong;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UmlModelDaoCustomImpl implements UmlModelDaoCustom {

  private final EntityManager em;

  public UmlModelDaoCustomImpl(EntityManager em) {
    this.em = em;
  }

  @Override
  public List<UmlModelSummary> findAll(String nameToSearch, UmlModelType typeToSearch,
      Long projectIdToSearch) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Tuple> cq = cb.createTupleQuery();
    Root<UmlModel> umlModelRoot = cq.from(UmlModel.class);

    List<Predicate> conditions = new ArrayList<>();
    if (nameToSearch != null) {
      conditions.add(cb.like(cb.lower(umlModelRoot.get(UmlModel_.name)),
          "%" + nameToSearch.toLowerCase() + "%"));
    }
    if (typeToSearch != null) {
      conditions.add(cb.equal(umlModelRoot.get(UmlModel_.type), typeToSearch));
    }
    if (projectIdToSearch != null) {
      Join<UmlModel, Project> projectJoin = umlModelRoot.join(UmlModel_.project);
      conditions.add(cb.equal(projectJoin.get(Project_.id), projectIdToSearch));
    }

    TypedQuery<Tuple> typedQuery = em.createQuery(cq
        .select(cb.tuple(
            umlModelRoot.get(UmlModel_.id),
            umlModelRoot.get(UmlModel_.name),
            umlModelRoot.get(UmlModel_.type),
            umlModelRoot.get(UmlModel_.project).get(Project_.id)))
        .where(conditions.toArray(new Predicate[]{}))
        .orderBy(cb.asc(umlModelRoot.get(UmlModel_.id))));
    List<Tuple> result = typedQuery.getResultList();

    List<UmlModelSummary> umlModelSummaries = new ArrayList<>();
    for (Tuple t : result) {
      long id = t.get(0, Long.class);
      String name = t.get(1, String.class);
      UmlModelType type = t.get(2, UmlModelType.class);
      long projectId = t.get(3, Long.class);
      UmlModelSummary umlModelSummary = new UmlModelSummary();
      umlModelSummary.setId(id);
      umlModelSummary.setName(name);
      umlModelSummary.setType(type);
      umlModelSummary.setProjectId(projectId);
      umlModelSummaries.add(umlModelSummary);
    }
    return umlModelSummaries;
  }

  @Override
  public Optional<UmlModelDetails> findDetailsById(long id) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Tuple> cq = cb.createTupleQuery();
    Root<UmlModel> umlModelRoot = cq.from(UmlModel.class);

    List<Predicate> conditions = new ArrayList<>();
    conditions.add(cb.equal(umlModelRoot.get(UmlModel_.id), id));

    TypedQuery<Tuple> typedQuery = em.createQuery(cq
        .select(cb.tuple(
            umlModelRoot.get(UmlModel_.name),
            umlModelRoot.get(UmlModel_.description),
            umlModelRoot.get(UmlModel_.createdOn),
            umlModelRoot.get(UmlModel_.modifiedOn),
            umlModelRoot.get(UmlModel_.type),
            umlModelRoot.get(UmlModel_.project).get(Project_.id)))
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
    UmlModelType type = t.get(4, UmlModelType.class);
    long projectId = t.get(5, Long.class);
    UmlModelDetails umlModelDetails = new UmlModelDetails();
    umlModelDetails.setId(id);
    umlModelDetails.setName(name);
    umlModelDetails.setDescription(description);
    umlModelDetails.setCreatedOn(createdOn);
    umlModelDetails.setModifiedOn(modifiedOn);
    umlModelDetails.setType(type);
    umlModelDetails.setProjectId(projectId);
    return Optional.of(umlModelDetails);
  }

  @Override
  public Map<byte[], UmlModelType> findContentByProject(long projectId, Long excludedUmlModelId) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Tuple> cq = cb.createTupleQuery();
    Root<UmlModel> umlModelRoot = cq.from(UmlModel.class);

    List<Predicate> conditions = new ArrayList<>();
    Join<UmlModel, Project> projectJoin = umlModelRoot.join(UmlModel_.project);
    conditions.add(cb.equal(projectJoin.get(Project_.id), projectId));
    if (excludedUmlModelId != null) {
      conditions.add(cb.notEqual(umlModelRoot.get(UmlModel_.id), excludedUmlModelId));
    }

    TypedQuery<Tuple> typedQuery = em.createQuery(cq
        .select(cb.tuple(
            umlModelRoot.get(UmlModel_.content),
            umlModelRoot.get(UmlModel_.type)))
        .where(conditions.toArray(new Predicate[]{})));
    List<Tuple> result = typedQuery.getResultList();

    Map<byte[], UmlModelType> contents = new HashMap<>();
    for (Tuple t : result) {
      byte[] content = t.get(0, byte[].class);
      UmlModelType type = t.get(1, UmlModelType.class);
      contents.put(content, type);
    }
    return contents;
  }

  @Override
  public OptionalLong findIdByTypeAndProject(UmlModelType type, long projectId) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Tuple> cq = cb.createTupleQuery();
    Root<UmlModel> umlModelRoot = cq.from(UmlModel.class);

    List<Predicate> conditions = new ArrayList<>();
    conditions.add(cb.equal(umlModelRoot.get(UmlModel_.type), type));
    Join<UmlModel, Project> projectJoin = umlModelRoot.join(UmlModel_.project);
    conditions.add(cb.equal(projectJoin.get(Project_.id), projectId));

    TypedQuery<Tuple> typedQuery = em.createQuery(cq
        .select(cb.tuple(
            umlModelRoot.get(UmlModel_.id)))
        .where(conditions.toArray(new Predicate[]{})));
    Tuple t;
    try {
      t = typedQuery.getSingleResult();
    } catch (NoResultException e) {
      return OptionalLong.empty();
    }

    long id = t.get(0, Long.class);
    return OptionalLong.of(id);
  }
}
