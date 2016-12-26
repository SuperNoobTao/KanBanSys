package cc.superliar.vo;

import cc.superliar.annotation.NotNullField;
import cc.superliar.constant.ResultConstant;
import cc.superliar.enums.OperationType;

import javax.persistence.Column;
import javax.persistence.Id;


/**
 * Created by shentao on 2016/12/26.
 */
public class ManageVO {

    private String code = ResultConstant.OK;

    private String message = "Successful operation";

    private int id;  //唯一自增配对ID

    private int url;     //url id

    private String device;     //device id

    private String content;   //content

    private String description;  //备注


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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ManageVO() {
    }

    @Override
    public String toString() {
        return "ManageVO{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", device='" + device + '\'' +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
