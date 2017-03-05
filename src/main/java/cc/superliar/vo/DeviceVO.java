package cc.superliar.vo;

import cc.superliar.annotation.NotNullField;
import cc.superliar.constant.ResultConstant;
import cc.superliar.enums.OperationType;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by shentao on 2016/12/20.
 */
public class DeviceVO implements Serializable{

    private static final long serialVersionUID = 842497757285997959L;

    private String code = ResultConstant.OK;

    private String message = "Successful operation";

    private String id; // device's ID

    private String name;

    private String location;

    private String screenSize;

    private String screenNum;

    private String styleid;

    private String stylename;

    private String description;

    private String text ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getScreenNum() {
        return screenNum;
    }

    public void setScreenNum(String screenNum) {
        this.screenNum = screenNum;
    }

    public String getStyleid() {
        return styleid;
    }

    public void setStyleid(String styleid) {
        this.styleid = styleid;
    }

    public String getStylename() {
        return stylename;
    }

    public void setStylename(String stylename) {
        this.stylename = stylename;
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
