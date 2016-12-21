package cc.superliar.vo;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Created by shentao on 2016/12/21.
 */
public class StyleVO implements Serializable{

    private static final long serialVersionUID = 1157692044436647370L;

    private Long id;

    private String time;

    private String mode;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
