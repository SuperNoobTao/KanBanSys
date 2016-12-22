package cc.superliar.po;

import cc.superliar.enums.ValidFlag;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by shentao on 2016/12/21.
 */

@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "tb_style")
public class Style implements Serializable{

    private static final long serialVersionUID = -1062441922780393067L;

    @Id()
    @SequenceGenerator(name = "styles_seq", sequenceName = "styles_seq", allocationSize = 1)
    @GeneratedValue(generator = "styles_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "style_id")
    private Long id;

    @Column(name = "style_stime")
    private String time;

    @Column(name = "style_smode")
    private String mode;

    @CreatedDate
    @Column(name = "style_created_date")
    private LocalDateTime createDate=LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "style_last_modified_date")
    private LocalDateTime lastModifiedDate=LocalDateTime.now();

    @Column(name = "style_description")
    private String description;

    @Column(name = "style_valid_flag")
    private ValidFlag validFlag= ValidFlag.VALID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

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

    public Style() {
    }
}