package cc.superliar.vo;

import cc.superliar.constant.ResultConstant;

import java.io.Serializable;

/**
 * Created by shentao on 2016/11/29.
 */
public class UserVO implements Serializable {

    private static final long serialVersionUID = 6597728015488383528L;

    private String code = ResultConstant.OK;

    private String message = "Successful operation";

    private Long id;

    private String name;

    private String account;

    private String description;

    private String text;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getText() {
        return name;
    }

    public void setText(String name) {
        this.text = name;
    }
}
