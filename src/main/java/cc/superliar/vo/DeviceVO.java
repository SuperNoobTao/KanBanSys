package cc.superliar.vo;

import cc.superliar.annotation.NotNullField;
import cc.superliar.enums.OperationType;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by shentao on 2016/12/20.
 */
public class DeviceVO implements Serializable{

    private static final long serialVersionUID = 842497757285997959L;

    private String id; // device's ID

    private String location;

    private int screenSize;

    private int screenNum;

    private int styleid;

    private String description;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
