package cc.superliar.param;

import cc.superliar.annotation.NotNullField;
import cc.superliar.enums.OperationType;

import javax.validation.constraints.Size;

/**
 * Created by shentao on 2016/12/20.
 */
public class UrlParam extends BaseParam{

    private static final long serialVersionUID = 3706313295218790502L;

    @NotNullField(value = {OperationType.UPDATE, OperationType.DELETE}, message = "id cannot be null.")
    private Long id;

    @NotNullField(value = OperationType.CREATE, message = "content cannot be null.")
    private String content;

    private String description;

    private String deviceIds;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDeviceIds() {
        return deviceIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeviceIds(String deviceIds) {
        this.deviceIds = deviceIds;
    }

    public UrlParam(Long id) {
        this.id = id;
    }

    public UrlParam() {
    }


}
