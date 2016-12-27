package cc.superliar.vo;

import cc.superliar.constant.ResultConstant;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Created by shentao on 2016/12/20.
 */
public class UrlVO implements Serializable{

    private static final long serialVersionUID = 170320508720525775L;

    private String code = ResultConstant.OK;

    private String message = "Successful operation";

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

    @Override
    public String toString() {
        return "UrlVO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}


