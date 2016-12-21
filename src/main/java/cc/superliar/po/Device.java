package cc.superliar.po;

import cc.superliar.enums.ValidFlag;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
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
    private String screenSize;

    @Column(name = "device_screen_num")
    private String screenNum;

    @Column(name = "device_styleid")
    private String styleid="0";

    @CreatedDate
    @Column(name = "device_created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "device_description")
    private String description;

    @LastModifiedDate
    @Column(name = "device_last_modified_date")
    private LocalDateTime lastModifiedDate= LocalDateTime.now();

    @Column(name = "device_vaild_flag")
    private ValidFlag validFlag = ValidFlag.VALID;

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

    public String getStyleid() {
        return styleid;
    }

    public void setStyleid(String styleid) {
        this.styleid = styleid;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public ValidFlag getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(ValidFlag validFlag) {
        this.validFlag = validFlag;
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
