package cc.superliar.po;

import cc.superliar.controller.DeviceController;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by shentao on 2016/12/24.
 */

@Entity

@Table(name="tb_device_has_url")
public class Manage implements Serializable{

    private static final long serialVersionUID = -6658950843575760800L;

    @EmbeddedId
    private ManagePK id;

    public Manage() {}

    public Manage(int url, String device) {
        this.id = new ManagePK(url, device);
    }

    public ManagePK getId() {
        return id;
    }

    public void setId(ManagePK id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Manage{" +
                "device=" + id.getDevice() +
                ",url=" + id.getUrl() +
                '}';
    }
}
