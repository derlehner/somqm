package at.ac.tuwien.big.ame.somqm.server.service;

import at.ac.tuwien.big.ame.somqm.server.dto.project.CreateProjectRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.project.ProjectDetails;
import at.ac.tuwien.big.ame.somqm.server.dto.project.ProjectSummary;
import at.ac.tuwien.big.ame.somqm.server.dto.project.SearchProjectsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.project.UpdateProjectRequest;
import at.ac.tuwien.big.ame.somqm.server.model.Project;
import java.util.List;
import java.util.NoSuchElementException;

public interface ProjectService {

  Project create(CreateProjectRequest request) throws IllegalArgumentException;

  void update(long id, UpdateProjectRequest request)
      throws IllegalArgumentException, NoSuchElementException;

  /**
   * Assigned uml-models get deleted too.
   */
  void delete(long id) throws NoSuchElementException;

  List<ProjectSummary> search(SearchProjectsRequest request) throws IllegalArgumentException;

  Project get(long id) throws NoSuchElementException;

  ProjectDetails getDetails(long id) throws NoSuchElementException;

}
