package cc.superliar.param;

import cc.superliar.annotation.NotNullField;
import cc.superliar.enums.OperationType;

/**
 * Created by shentao on 2016/12/26.
 */
public class ManageParam extends BaseParam{

    private static final long serialVersionUID = -7622432345305370135L;

    @NotNullField(value = {OperationType.CREATE, OperationType.DELETE}, message = "url cannot be null.")
    private int url;
    @NotNullField(value = {OperationType.CREATE, OperationType.DELETE}, message = "device cannot be null.")
    private String device;

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
}
