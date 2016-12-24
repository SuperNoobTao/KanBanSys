package cc.superliar.po;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by shentao on 2016/12/24.
 */
@Embeddable
public class ManagePK implements Serializable {

    private static final long serialVersionUID = -4930270748689351426L;

    @Column(name = "url_id")
    private int url;

    @Column(name = "device_id")
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

    public ManagePK(int url, String device) {
        this.url = url;
        this.device = device;
    }

    public ManagePK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManagePK)) return false;

        ManagePK managePK = (ManagePK) o;

        if (getUrl() != managePK.getUrl()) return false;
        return getDevice().equals(managePK.getDevice());
    }

    @Override
    public int hashCode() {
        int result = getUrl();
        result = 31 * result + getDevice().hashCode();
        return result;
    }
}
