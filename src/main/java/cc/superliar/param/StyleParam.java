package cc.superliar.param;

import cc.superliar.annotation.NotNullField;
import cc.superliar.enums.OperationType;

/**
 * Created by shentao on 2016/12/21.
 */
public class StyleParam extends BaseParam {

    @NotNullField(value = {OperationType.UPDATE, OperationType.DELETE}, message = "id cannot be null.")
    private Long id;

    private String name;

    @NotNullField(value = OperationType.CREATE, message = "time cannot be null.")
    private String time;

    @NotNullField(value = OperationType.CREATE, message = "mode cannot be null.")
    private String mode;

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

    public StyleParam(Long id) {
        this.id = id;
    }

    public StyleParam() {
    }
}
