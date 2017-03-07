package cc.superliar.vo;

import cc.superliar.po.Url;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shentao on 2017/3/1.
 */
public class DataVO implements Serializable {

    String code;
    String message;

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

    public DataVO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String id; // device's ID

    private String name;

    private String location;

    private String screenSize;

    private String screenNum;



    private StyleVO styleVO;

    private String description;

    private List<UrlVO> urls;

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

    public StyleVO getStyleVO() {
        return styleVO;
    }

    public void setStyleVO(StyleVO styleVO) {
        this.styleVO = styleVO;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UrlVO> getUrls() {
        return urls;
    }

    public void setUrls(List<UrlVO> urls) {
        this.urls = urls;
    }
}


