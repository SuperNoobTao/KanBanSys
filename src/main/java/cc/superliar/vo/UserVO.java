package cc.superliar.vo;

import java.io.Serializable;

/**
 * Created by shentao on 2016/11/29.
 */
public class UserVO implements Serializable {

    private static final long serialVersionUID = 6597728015488383528L;

    private Long id;

    private String name;

    private String account;

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

}
