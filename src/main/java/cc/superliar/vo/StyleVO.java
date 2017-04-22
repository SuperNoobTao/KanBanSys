package cc.superliar.vo;

import cc.superliar.constant.ResultConstant;

import java.io.Serializable;

/**
 * Created by shentao on 2016/12/21.
 */
public class StyleVO implements Serializable{

    private static final long serialVersionUID = 1157692044436647370L;

    private String code = ResultConstant.OK;

    private String message = "Successful operation";

    private Long id;

    private String name;

    private String speed;

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
//     if (Integer.parseInt(mode)==0) {
//             this.mode="NO-ACTION";
//             }
//             if (Integer.parseInt(mode)==1) {
//             this.mode="Horizontal";
//             }
//             if (Integer.parseInt(mode)==2) {
//             this.mode="Vertical";
//             }