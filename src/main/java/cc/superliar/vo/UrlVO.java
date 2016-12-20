package cc.superliar.vo;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Created by shentao on 2016/12/20.
 */
public class UrlVO implements Serializable{

    private static final long serialVersionUID = 170320508720525775L;

    private Long id;

    private String content;

    private String description;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}


