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
 * Authorized roles, provide for spring security.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/23/15
 * @since JDK1.8
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "tb_role")
@NamedEntityGraph(name = "Role.resources", attributeNodes = @NamedAttributeNode("resources"))
public class Role implements GrantedAuthority, Serializable {

  private static final long serialVersionUID = -5193344128221526323L;

  @Id
  @SequenceGenerator(name = "roles_seq", sequenceName = "roles_seq", allocationSize = 1)
  @GeneratedValue(generator = "roles_seq", strategy = GenerationType.SEQUENCE)
  @Column(name = "role_id")
  private Long id;
  @Column(name = "role_name")
  private String name;
  @Column(name = "role_description")
  private String description;
  @Column(name = "role_created_date")
  private LocalDateTime createDate=LocalDateTime.now();
  @Column(name = "role_last_modified_date")
  private LocalDateTime lastModifiedDate=LocalDateTime.now();
  @Column(name = "role_valid_flag")
  private ValidFlag validFlag = ValidFlag.VALID;
  @Column(name = "role_version")
  private int version;
  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles", cascade = {CascadeType.REFRESH})
  private Set<User> users = new HashSet<>();
  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
  @JoinTable(name = "tb_role_has_resourse",
          joinColumns = {@JoinColumn(name = "role_id")},
          inverseJoinColumns = {@JoinColumn(name = "resource_id")})
  private Set<Resource> resources = new HashSet<>();


  @Override
  public String getAuthority() {
    return name;
  }

  public Role() {
  }

  public Role(String name, String description) {
    this.name = name;
    this.description = description;
  }

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

  public LocalDateTime getCreateDate() {
    return createDate;
  }

  public void setCreateDate(LocalDateTime createDate) {
    this.createDate = createDate;
  }

  public LocalDateTime getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
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

  public Set<Resource> getResources() {
    return resources;
  }

  public void setResources(Set<Resource> resources) {
    this.resources = resources;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }
}
