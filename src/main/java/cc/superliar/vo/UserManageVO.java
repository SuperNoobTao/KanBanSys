package cc.superliar.vo;

import cc.superliar.constant.ResultConstant;

/**
 * Created by shentao on 2017/3/9.
 */
public class UserManageVO {

    private String code = ResultConstant.OK;

    private String message = "Successful operation";

    private int id;  //唯一自增配对ID

    private int role;     //role id

    private int user;     //user id

    private String name;   //name

    private String description;  //备注

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
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
}
