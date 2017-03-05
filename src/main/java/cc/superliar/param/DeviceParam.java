package cc.superliar.param;

import cc.superliar.annotation.NotNullField;
import cc.superliar.enums.OperationType;

import javax.validation.constraints.Size;

/**
 * Created by shentao on 2016/12/20.
 */
public class DeviceParam extends BaseParam{

    private static final long serialVersionUID = -177088332110942844L;

    @NotNullField(value = {OperationType.CREATE,OperationType.UPDATE, OperationType.DELETE}, message = "udid cannot be null.")
    private String id; // device's ID

    @NotNullField(value = OperationType.CREATE, message = "name cannot be null.")
    private String name;

    @NotNullField(value = OperationType.CREATE, message = "location cannot be null.")
    private String location;

    @NotNullField(value = OperationType.CREATE, message = "screenSize cannot be null.")
    private String screenSize;

    @NotNullField(value = OperationType.CREATE, message = "screenNum cannot be null.")
    private String screenNum;

    private String styleid;

    private String stylename;

    private String description;

    private String urlIds;


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

    public String getUrlIds() {
        return urlIds;
    }

    public void setUrlIds(String urlIds) {
        this.urlIds = urlIds;
    }

    public DeviceParam(String id) {
        this.id = id;
    }

    public DeviceParam() {
    }
}
