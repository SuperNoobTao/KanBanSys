package cc.superliar.vo;

import cc.superliar.constant.ResultConstant;

import java.io.Serializable;

/**
 * Created by shentao on 2016/11/29.
 */
public class RoleVO implements Serializable {

    private static final long serialVersionUID = 1444065316565469644L;

    private String code = ResultConstant.OK;

    private String message = "Successful operation";

    private Long id;

    private String name;

    private String description;

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
}