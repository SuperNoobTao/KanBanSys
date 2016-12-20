package cc.superliar.po;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by shentao on 2016/12/20.
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "tb_device")
@NamedEntityGraph(name = "Device.urls", attributeNodes = @NamedAttributeNode("urls"))
public class Device implements Serializable{

    private static final long serialVersionUID = 6500601540965188191L;

    @Id
    @Column(name = "device_id")
    private String id;

    @Column(name = "device_name")
    private String name;

    @Column(name = "device_location")
    private String location;

    @Column(name = "device_screen_size")
    private int screenSize;

    @Column(name = "device_screen_num")
    private int screenNum;

    @Column(name = "device_styleid")
    private int styleid;

    @CreatedDate
    @Column(name = "device_created_date")
    private Date createdDate;

    @Column(name = "device_description")
    private Date description;

    @LastModifiedDate
    @Column(name = "device_last_modified_date")
    private Date lastModifiedDate;

    @Column(name = "device_vaild_flag")
    private int validFlag;

    @Column(name = "device_version")
    private int version;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "tb_device_has_url",
            joinColumns = {@JoinColumn(name = "device_id")},
            inverseJoinColumns = {@JoinColumn(name = "url_id")})
    private Set<Url> urls = new HashSet<>();


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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getDescription() {
        return description;
    }

    public void setDescription(Date description) {
        this.description = description;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(int validFlag) {
        this.validFlag = validFlag;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Set<Url> getUrls() {
        return urls;
    }

    public void setUrls(Set<Url> urls) {
        this.urls = urls;
    }

    public Device() {
    }



}
