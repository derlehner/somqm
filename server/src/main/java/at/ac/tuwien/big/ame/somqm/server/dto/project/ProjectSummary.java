package at.ac.tuwien.big.ame.somqm.server.dto.project;

public class ProjectSummary {

  private long id;
  private String name;

  public ProjectSummary() {

  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "ProjectSummary{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
