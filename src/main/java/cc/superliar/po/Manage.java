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

    @Id
    @SequenceGenerator(name = "manage_id_seq", sequenceName = "manage_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "manage_id_seq", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "url_id")
    private int url;

    @Column(name = "device_id")
    private String device;





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

    @Override
    public String toString() {
        return "Manage{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", device='" + device + '\'' +
                '}';
    }

    public Manage() {
    }

    public Manage(int url, String device) {
        this.url = url;
        this.device = device;
    }
}
