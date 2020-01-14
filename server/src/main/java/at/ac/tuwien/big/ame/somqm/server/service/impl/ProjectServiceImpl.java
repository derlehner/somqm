package at.ac.tuwien.big.ame.somqm.server.service.impl;

import at.ac.tuwien.big.ame.somqm.server.dao.ProjectDao;
import at.ac.tuwien.big.ame.somqm.server.dao.UmlModelDao;
import at.ac.tuwien.big.ame.somqm.server.dto.project.CreateProjectRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.project.ProjectDetails;
import at.ac.tuwien.big.ame.somqm.server.dto.project.ProjectSummary;
import at.ac.tuwien.big.ame.somqm.server.dto.project.SearchProjectsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.project.UpdateProjectRequest;
import at.ac.tuwien.big.ame.somqm.server.model.Project;
import at.ac.tuwien.big.ame.somqm.server.service.ProjectService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

  private final ProjectDao projectDao;
  private final UmlModelDao umlModelDao;

  @Autowired
  public ProjectServiceImpl(ProjectDao projectDao, UmlModelDao umlModelDao) {
    this.projectDao = projectDao;
    this.umlModelDao = umlModelDao;
  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
  public Project create(CreateProjectRequest request) throws IllegalArgumentException {
    if (request == null) {
      throw new IllegalArgumentException("Supplied request is invalid");
    }
    if (request.getName() == null || request.getName().trim().isEmpty()) {
      throw new IllegalArgumentException("Supplied name is invalid");
    }
    request.setName(request.getName().trim());
    if (request.getDescription() == null) {
      throw new IllegalArgumentException("Supplied description is invalid");
    }
    request.setDescription(request.getDescription().trim());
    if (projectDao.existsByName(request.getName())) {
      throw new IllegalArgumentException(
          "Project with supplied name '" + request.getName() + "' already exists");
    }
    Project newProject = new Project();
    newProject.setName(request.getName());
    newProject.setDescription(request.getDescription());
    return projectDao.save(newProject);
  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
  public void update(long id, UpdateProjectRequest request)
      throws IllegalArgumentException, NoSuchElementException {
    if (request == null) {
      throw new IllegalArgumentException("Supplied request is invalid");
    }
    if (request.getName() == null || request.getName().trim().isEmpty()) {
      throw new IllegalArgumentException("Supplied name is invalid");
    }
    request.setName(request.getName().trim());
    if (request.getDescription() == null) {
      throw new IllegalArgumentException("Supplied description is invalid");
    }
    request.setDescription(request.getDescription().trim());
    Project existingProject = projectDao.findByName(request.getName());
    if (existingProject != null && existingProject.getId() != id) {
      throw new IllegalArgumentException(
          "Project with supplied name '" + request.getName() + "' already exists");
    }
    if (existingProject == null) {
      Optional<Project> projectOptional = projectDao.findById(id);
      if (projectOptional.isPresent()) {
        existingProject = projectOptional.get();
      } else {
        throw new NoSuchElementException("No project found with supplied id " + id);
      }
    }
    existingProject.setName(request.getName());
    existingProject.setDescription(request.getDescription());
    projectDao.save(existingProject);
  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
  public void delete(long id) throws NoSuchElementException {
    Optional<Project> projectOptional = projectDao.findById(id);
    if (!projectOptional.isPresent()) {
      throw new NoSuchElementException("No project found with supplied id " + id);
    }
    Project project = projectOptional.get();
    umlModelDao.deleteByProject(project.getId());
    projectDao.delete(project);
  }

  @Override
  public List<ProjectSummary> search(SearchProjectsRequest request)
      throws IllegalArgumentException {
    if (request == null) {
      throw new IllegalArgumentException("Supplied request is invalid");
    }
    return projectDao.findAll(request.getNameToSearch());
  }

  @Override
  public Project get(long id) throws NoSuchElementException {
    Optional<Project> projectOptional = projectDao.findById(id);
    if (!projectOptional.isPresent()) {
      throw new NoSuchElementException("No project found with supplied id " + id);
    }
    return projectOptional.get();
  }

  @Override
  public ProjectDetails getDetails(long id) throws NoSuchElementException {
    Optional<ProjectDetails> projectDetailsOptional = projectDao.findDetailsById(id);
    if (!projectDetailsOptional.isPresent()) {
      throw new NoSuchElementException("No project found with supplied id " + id);
    }
    return projectDetailsOptional.get();
  }

}
