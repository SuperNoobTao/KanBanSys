package cc.superliar.po;

import cc.superliar.enums.ValidFlag;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by shentao on 2016/12/20.
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "tb_url")
public class Url implements Serializable{

    private static final long serialVersionUID = 8105387418071586262L;

    @Id()
    @SequenceGenerator(name = "urls_seq", sequenceName = "urls_seq", allocationSize = 1)
    @GeneratedValue(generator = "urls_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "url_id")
    private Long id;

    @Column(name = "url_name")
    private String name;

    @Column(name = "url_content")
    private String content;

    @CreatedDate
    @Column(name = "url_created_date")
    private LocalDateTime createDate=LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "url_last_modified_date")
    private LocalDateTime lastModifiedDate=LocalDateTime.now();

    @Column(name = "url_description")
    private String description;

    @Column(name = "url_valid_flag")
    private ValidFlag validFlag= ValidFlag.VALID;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "urls", cascade = {CascadeType.REFRESH})
    private Set<Device> devices = new HashSet<>();

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ValidFlag getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(ValidFlag validFlag) {
        this.validFlag = validFlag;
    }

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

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Url() {
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", description='" + description + '\'' +
                ", validFlag=" + validFlag +
                ", devices=" + devices +
                '}';
    }
}
