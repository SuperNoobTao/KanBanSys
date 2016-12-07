package cc.superliar.param;

import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Base param.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 8/19/15
 * @since JDK1.8
 */
public class BaseParam implements Serializable {

  private static final Set<String> baseFields = new HashSet<>();

  static {}

  private static final String EQUAL = "=";

  private static final long serialVersionUID = -103658650614029839L;

  private Integer pageNo;

  private Integer pageSize = 20;

  private String sortBy;

  private String sign;

  private UserDetails currentUser;

  public Integer getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public String getSortBy() {
    return sortBy;
  }

  public void setSortBy(String sortBy) {
    this.sortBy = sortBy;
  }

  public UserDetails getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(UserDetails currentUser) {
    this.currentUser = currentUser;
  }




}
