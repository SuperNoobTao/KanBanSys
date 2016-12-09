package cc.superliar.param;


import cc.superliar.annotation.NotNullField;
import cc.superliar.enums.OperationType;

public class RoleParam extends BaseParam {

    private static final long serialVersionUID = 8542867394907970893L;

    @NotNullField(value = {OperationType.UPDATE, OperationType.DELETE}, message = "id cannot be null.")
    private Long id; // role's ID.

    @NotNullField(value = OperationType.CREATE, message = "name cannot be null.")
    private String name; // role's name

    private String description;

    private String userIds; // user ids string

    private String resourceIds; // resource ids string

    public RoleParam() {}

    public RoleParam(Long id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }
}
