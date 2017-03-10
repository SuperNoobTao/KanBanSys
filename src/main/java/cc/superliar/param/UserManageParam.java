package cc.superliar.param;

import cc.superliar.annotation.NotNullField;
import cc.superliar.enums.OperationType;

/**
 * Created by shentao on 2017/3/9.
 */
public class UserManageParam extends BaseParam {


    private static final long serialVersionUID = 2991287039898696064L;
    @NotNullField(value = {OperationType.CREATE, OperationType.DELETE}, message = "user cannot be null.")
    private int user;
    @NotNullField(value = {OperationType.CREATE, OperationType.DELETE}, message = "role cannot be null.")
    private int role;

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
