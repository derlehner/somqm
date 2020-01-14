package at.ac.tuwien.big.ame.somqm.server.rest;

import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.CreateUmlModelRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.SearchUmlModelsRequest;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelDetails;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UmlModelSummary;
import at.ac.tuwien.big.ame.somqm.server.dto.umlmodel.UpdateUmlModelRequest;
import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;
import at.ac.tuwien.big.ame.somqm.server.service.ServiceException;
import at.ac.tuwien.big.ame.somqm.server.service.UmlModelService;
import com.google.common.base.Charsets;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/umlmodels")
public class UmlModelController {

  private final UmlModelService umlModelService;

  @Autowired
  public UmlModelController(UmlModelService umlModelService) {
    this.umlModelService = umlModelService;
  }

  @PostMapping(value = "/")
  @ApiOperation(value = "Creates a new uml-model and returns its ID")
  public long create(@RequestBody CreateUmlModelRequest request) {
    return umlModelService.create(request).getId();
  }

  @PutMapping(value = "/{id}")
  @ApiOperation(value = "Updates an existing uml-model")
  public void update(@PathVariable("id") long id, @RequestBody UpdateUmlModelRequest request) {
    umlModelService.update(id, request);
  }

  @PutMapping(value = "/{id}/content", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiOperation(value = "Updates the content of an existing uml-model", notes = "Per project only one model per type (e.g. class-diagram) is permitted.")
  public void updateContent(@PathVariable("id") long id, @RequestPart("file") MultipartFile file)
      throws ServiceException {
    byte[] content;
    try {
      content = file.getBytes();
    } catch (IOException e) {
      throw new ServiceException("Getting bytes of supplied file failed", e);
    }
    umlModelService.updateContent(id, content);
  }

  @PutMapping(value = "/{id}/contentfrompath")
  @ApiOperation(value = "Updates the content of an existing uml-model", notes = "Per project only one model per type (e.g. class-diagram) is permitted.")
  public void updateContentFromPath(@PathVariable("id") long id, @RequestBody String path)
      throws ServiceException {
    byte[] content;
    try {
      content = Files.readAllBytes(Paths.get(path));
    } catch (IOException e) {
      throw new ServiceException("Getting bytes of supplied file failed", e);
    }
    umlModelService.updateContent(id, content);
  }

  @DeleteMapping(value = "/{id}")
  @ApiOperation(value = "Deletes an existing uml-model")
  public void delete(@PathVariable("id") long id) {
    umlModelService.delete(id);
  }

  @PostMapping(value = "/search")
  @ApiOperation(value = "Returns all existing uml-models matching the supplied search-filter")
  public List<UmlModelSummary> search(@RequestBody SearchUmlModelsRequest request) {
    return umlModelService.search(request);
  }

  @GetMapping(value = "/{id}")
  @ApiOperation(value = "Returns an existing uml-model")
  public UmlModelDetails get(@PathVariable("id") long id) {
    return umlModelService.getDetails(id);
  }

  @GetMapping(value = "/{id}/content")
  @ApiOperation(value = "Returns the content of an existing uml-model")
  public ResponseEntity<byte[]> getContent(@PathVariable("id") long id) {
    byte[] content = umlModelService.getContent(id);
    HttpHeaders respHeaders = new HttpHeaders();
    respHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    String fileName = "model_" + id + ".uml";
    respHeaders.setContentDispositionFormData("attachment",
        new String(fileName.getBytes(Charsets.UTF_8), Charsets.ISO_8859_1));
    respHeaders.setContentLength(content.length);
    // Disable caching
    respHeaders.setCacheControl("no-cache, no-store, must-revalidate"); // HTTP 1.1
    respHeaders.setPragma("no-cache"); // HTTP 1.0
    respHeaders.setExpires(0); // Proxies
    return ResponseEntity.ok().headers(respHeaders).body(content);
  }

  @GetMapping(value = "/supportedTypes")
  @ApiOperation(value = "Returns supported uml-model types")
  public List<UmlModelType> getSupportedTypes() {
    return umlModelService.getSupportedTypes();
  }

}
