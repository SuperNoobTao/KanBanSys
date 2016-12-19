package cc.superliar.po;


import cc.superliar.enums.ValidFlag;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import java.util.HashSet;
import java.util.Set;

/**
 * Authorized resources,provide for spring security.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "tb_resource")
public class Resource implements GrantedAuthority, Serializable {

  private static final long serialVersionUID = 6298843159549723556L;

  @Id
  @SequenceGenerator(name = "resources_seq", sequenceName = "resources_seq", allocationSize = 1)
  @GeneratedValue(generator = "resources_seq", strategy = GenerationType.SEQUENCE)
  @Column(name = "resource_id")
  private Long id;
  @Column(name = "resource_created_date")
  private LocalDateTime createDate=LocalDateTime.now();
  @Column(name = "resource_description")
  private String description;
  @Column(name = "resource_last_modified_date")
  private LocalDateTime lastModifiedDate=LocalDateTime.now();
  @Column(name = "resource_name")
  private String name;
  @Column(name = "resource_path")
  private String path;
  @Column(name = "resource_priority")
  private int priority;
  @Column(name = "resource_valid_flag")
  private ValidFlag validFlag= ValidFlag.VALID;
  @Column(name = "resource_version")
  private int version;
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "resources", cascade = {CascadeType.REFRESH})
  private Set<Role> roles = new HashSet<>();

  public Resource() {
  }

  public Resource(String name, String path, int priority, String description) {
    this.name = name;
    this.path = path;
    this.priority = priority;
    this.description = description;
  }
  @Override
  public String getAuthority() {
    return name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public ValidFlag getValidFlag() {
    return validFlag;
  }

  public void setValidFlag(ValidFlag validFlag) {
    this.validFlag = validFlag;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
}
