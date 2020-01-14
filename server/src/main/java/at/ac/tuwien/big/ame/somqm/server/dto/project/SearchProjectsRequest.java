package at.ac.tuwien.big.ame.somqm.server.dto.project;

import io.swagger.annotations.ApiModelProperty;

public class SearchProjectsRequest {

  @ApiModelProperty(required = false, notes = "Name or part of it")
  private String nameToSearch;

  public SearchProjectsRequest() {

  }

  public String getNameToSearch() {
    return nameToSearch;
  }

  public void setNameToSearch(String nameToSearch) {
    this.nameToSearch = nameToSearch;
  }

  @Override
  public String toString() {
    return "SearchProjectsRequest{" +
        "nameToSearch='" + nameToSearch + '\'' +
        '}';
  }
}
