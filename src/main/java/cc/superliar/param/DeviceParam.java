package cc.superliar.param;

import cc.superliar.annotation.NotNullField;
import cc.superliar.enums.OperationType;

import javax.validation.constraints.Size;

/**
 * Created by shentao on 2016/12/20.
 */
public class DeviceParam extends BaseParam{

    private static final long serialVersionUID = -177088332110942844L;

    @NotNullField(value = {OperationType.UPDATE, OperationType.DELETE}, message = "udid cannot be null.")
    private String udid; // device's ID

    @NotNullField(value = OperationType.CREATE, message = "location cannot be null.")
    @Size(min = 4, max = 50)
    private String location;

    @NotNullField(value = OperationType.CREATE, message = "screenSize cannot be null.")
    @Size(min = 4, max = 50)
    private int screenSize;

    @NotNullField(value = OperationType.CREATE, message = "screenNum cannot be null.")
    @Size(min = 4, max = 50)
    private int screenNum;

    private int styleid;

    private String description;

    private String urlIds;


    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(int screenSize) {
        this.screenSize = screenSize;
    }

    public int getScreenNum() {
        return screenNum;
    }

    public void setScreenNum(int screenNum) {
        this.screenNum = screenNum;
    }

    public int getStyleid() {
        return styleid;
    }

    public void setStyleid(int styleid) {
        this.styleid = styleid;
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

    public DeviceParam(String udid) {
        this.udid = udid;
    }

    public DeviceParam() {
    }
}
