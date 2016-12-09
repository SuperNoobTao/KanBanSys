package cc.superliar.param;

import cc.superliar.annotation.NotNullField;
import cc.superliar.enums.OperationType;

/**
 * Created by shentao on 2016/11/29.
 */
public class ResourceParam extends BaseParam {

    private static final long serialVersionUID = 8542867394907970893L;

    @NotNullField(value = {OperationType.UPDATE, OperationType.DELETE}, message = "id cannot be null.")
    private Long id; // role's ID.

    @NotNullField(value = OperationType.CREATE, message = "name cannot be null.")
    private String name; // role's name

    @NotNullField(value = OperationType.CREATE, message = "path cannot be null.")
    private String path;

    @NotNullField(value = OperationType.CREATE, message = "priority cannot be null.")
    private Integer priority;

    private String description;

    private String roleIds; // role ids string

    public ResourceParam() {}

    public ResourceParam(Long id) {
        this.id = id;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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
