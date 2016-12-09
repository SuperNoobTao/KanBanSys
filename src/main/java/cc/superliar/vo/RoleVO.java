package cc.superliar.vo;

import java.io.Serializable;

/**
 * Created by shentao on 2016/11/29.
 */
public class RoleVO implements Serializable {

    private static final long serialVersionUID = 1444065316565469644L;

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
}