package at.ac.tuwien.big.ame.somqm.server.dto.umlmodel;

import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;
import io.swagger.annotations.ApiModelProperty;

public class SearchUmlModelsRequest {

  @ApiModelProperty(required = false, notes = "Name or part of it")
  private String nameToSearch;

  @ApiModelProperty(required = false)
  private UmlModelType typeToSearch;

  @ApiModelProperty(required = false)
  private Long projectIdToSearch;

  public SearchUmlModelsRequest() {

  }

  public String getNameToSearch() {
    return nameToSearch;
  }

  public void setNameToSearch(String nameToSearch) {
    this.nameToSearch = nameToSearch;
  }

  public UmlModelType getTypeToSearch() {
    return typeToSearch;
  }

  public void setTypeToSearch(
      UmlModelType typeToSearch) {
    this.typeToSearch = typeToSearch;
  }

  public Long getProjectIdToSearch() {
    return projectIdToSearch;
  }

  public void setProjectIdToSearch(Long projectIdToSearch) {
    this.projectIdToSearch = projectIdToSearch;
  }

  @Override
  public String toString() {
    return "SearchUmlModelsRequest{" +
        "nameToSearch='" + nameToSearch + '\'' +
        ", typeToSearch=" + typeToSearch +
        ", projectIdToSearch=" + projectIdToSearch +
        '}';
  }
}
