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

    @NotNullField(value = OperationType.CREATE, message = "speed cannot be null.")
    private String speed;

    @NotNullField(value = OperationType.CREATE, message = "mode cannot be null.")
    private String mode;

    @NotNullField(value = OperationType.CREATE, message = "way cannot be null.")
    private String way;

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

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
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
