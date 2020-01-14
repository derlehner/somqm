package at.ac.tuwien.big.ame.somqm.server.util;

import at.ac.tuwien.big.ame.somqm.server.dto.project.CreateProjectRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.CreateUmlModelRequest;
import at.ac.tuwien.big.ame.somqm.server.model.Project;
import at.ac.tuwien.big.ame.somqm.server.model.UmlModel;
import at.ac.tuwien.big.ame.somqm.server.service.ProjectService;
import at.ac.tuwien.big.ame.somqm.server.service.ServiceException;
import at.ac.tuwien.big.ame.somqm.server.service.UmlModelService;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component
public class ExampleDataGenerator implements ApplicationListener<ApplicationReadyEvent> {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final ProjectService projectService;
  private final UmlModelService umlModelService;

  @Value("${somqm.generate-example-data}")
  private boolean generateExampleData;

  @Autowired
  public ExampleDataGenerator(ProjectService projectService, UmlModelService umlModelService) {
    this.projectService = projectService;
    this.umlModelService = umlModelService;
  }

  private static byte[] loadExampleDataContent(String fileName) {
    String resourceLocation = "example-data/" + fileName;
    Resource resource = new ClassPathResource(resourceLocation);
    byte[] content;
    try {
      content = FileCopyUtils.copyToByteArray(resource.getInputStream());
    } catch (IOException e) {
      throw new RuntimeException(
          "Failed to load resource '" + resourceLocation + "' as byte-array: " + e.getMessage(), e);
    }
    return content;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    if (!generateExampleData) {
      return;
    }
    logger.info("Generating example-data ...");
    try {
      generateExampleData();
    } catch (ServiceException e) {
      throw new RuntimeException("Failed to generate example-data: " + e.getMessage(), e);
    }
    logger.info("Example-data generated");
  }

  private void generateExampleData() throws ServiceException {
    Project exampleProject1 = createProject("Autonomous Car System",
        "This is a description for example project 1");

    UmlModel classDiagram = createUmlModel("class diagram", "classes of autonomous car system",
        "genmymodel/ame-showcase-classdiagram.xmi", exampleProject1.getId());
    UmlModel activityDiagram = createUmlModel("Example uml-model 2",
        "Example activity diagram model", "genmymodel/ame-showcase-activitydiagram.xmi",
        exampleProject1.getId());
    UmlModel stateMachineDiagram = createUmlModel("Example uml-model 3",
        "Example state machine diagram model", "genmymodel/ame-showcase-statemachinediagram.xmi",
        exampleProject1.getId());
  }

  private Project createProject(String name, String description) throws ServiceException {
    CreateProjectRequest createProjectRequest = new CreateProjectRequest();
    createProjectRequest.setName(name);
    createProjectRequest.setDescription(description);
    return projectService.create(createProjectRequest);
  }

  private UmlModel createUmlModel(String name, String description, String contentFileName,
      long projectId) throws ServiceException {
    CreateUmlModelRequest createUmlModelRequest = new CreateUmlModelRequest();
    createUmlModelRequest.setName(name);
    createUmlModelRequest.setDescription(description);
    createUmlModelRequest.setProjectId(projectId);
    UmlModel umlModel = umlModelService.create(createUmlModelRequest);
    byte[] content = loadExampleDataContent(contentFileName);
    umlModelService.updateContent(umlModel.getId(), content);
    return umlModelService.get(umlModel.getId());
  }
}
