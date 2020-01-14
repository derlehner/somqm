package at.ac.tuwien.big.ame.somqm.server.rest;

import at.ac.tuwien.big.ame.somqm.server.dto.project.CreateProjectRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.project.ProjectDetails;
import at.ac.tuwien.big.ame.somqm.server.dto.project.ProjectSummary;
import at.ac.tuwien.big.ame.somqm.server.dto.project.SearchProjectsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.project.UpdateProjectRequest;
import at.ac.tuwien.big.ame.somqm.server.service.ProjectService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/projects")
public class ProjectController {

  private final ProjectService projectService;

  @Autowired
  public ProjectController(ProjectService projectService) {
    this.projectService = projectService;
  }

  @PostMapping(value = "/")
  @ApiOperation(value = "Creates a new project and returns its ID")
  public long create(@RequestBody CreateProjectRequest request) {
    return projectService.create(request).getId();
  }

  @PutMapping(value = "/{id}")
  @ApiOperation(value = "Updates an existing project")
  public void update(@PathVariable("id") long id, @RequestBody UpdateProjectRequest request) {
    projectService.update(id, request);
  }

  @DeleteMapping(value = "/{id}")
  @ApiOperation(value = "Deletes an existing project (and its assigned uml-models)")
  public void delete(@PathVariable("id") long id) {
    projectService.delete(id);
  }

  @PostMapping(value = "/search")
  @ApiOperation(value = "Returns all existing projects matching the supplied search-filter")
  public List<ProjectSummary> search(@RequestBody SearchProjectsRequest request) {
    return projectService.search(request);
  }

  @GetMapping(value = "/{id}")
  @ApiOperation(value = "Returns an existing project")
  public ProjectDetails get(@PathVariable("id") long id) {
    return projectService.getDetails(id);
  }

}
