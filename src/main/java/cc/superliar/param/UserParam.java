package cc.superliar.param;

import cc.superliar.annotation.NotNullField;
import cc.superliar.enums.OperationType;

import javax.validation.constraints.Size;

/**
 * Created by shentao on 2016/11/29.
 */
public class UserParam extends BaseParam {

    private static final long serialVersionUID = -9153801716112918626L;

    @NotNullField(value = {OperationType.UPDATE, OperationType.DELETE}, message = "id cannot be null.")
    private Long id; // user's ID

    @NotNullField(value = OperationType.CREATE, message = "account cannot be null.")
    @Size(min = 4, max = 50)
    private String account; // account

    @NotNullField(value = OperationType.CREATE, message = "pwd cannot be null.")
    @Size(min = 4, max = 50)
    private String pwd; // pwd

    @NotNullField(value = OperationType.CREATE, message = "name cannot be null.")
    @Size(min = 1, max = 50)
    private String name; // name

    private String description;

    private String roleIds; // role ids string

    private int version;

    public UserParam() {}

    public UserParam(Long id) {
        this.id = id;
    }

    public UserParam(String account) {
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
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

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }


}
