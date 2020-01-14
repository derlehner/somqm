package at.ac.tuwien.big.ame.somqm.server.model;

import at.ac.tuwien.big.ame.somqm.server.model.enumeration.UmlModelType;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(name = "UniqueUmlModelTypesPerProject", columnNames = {"type", "project_id"})
})
public class UmlModel extends AbstractEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Basic
  @NotEmpty
  @Column(name = "name", nullable = false)
  private String name;

  @Basic
  @NotNull
  @Column(nullable = false)
  private String description;

  @Basic
  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private UmlModelType type;

  @Basic
  @Column(length = Integer.MAX_VALUE)
  private byte[] content;

  @ManyToOne(targetEntity = Project.class)
  @JoinColumn(name = "project_id", nullable = false)
  private Project project;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public UmlModelType getType() {
    return type;
  }

  public void setType(UmlModelType type) {
    this.type = type;
  }

  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  @Override
  public String toString() {
    return "UmlModel{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", createdOn=" + super.getCreatedOn() +
        ", modifiedOn=" + super.getModifiedOn() +
        ", type=" + type +
        ", content=" + "..." +
        ", project=" + project +
        '}';
  }
}
