package cc.superliar.po;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by shentao on 2017/3/9.
 */
@Entity
@Table(name="tb_user_has_role")
public class UserManage implements Serializable {

    private static final long serialVersionUID = -6658950843575760810L;

    @Id
    @SequenceGenerator(name = "usermanage_id_seq", sequenceName = "usermanage_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "usermanage_id_seq", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "role_id")
    private int role;

    @Column(name = "user_id")
    private int user;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public UserManage() {
    }

    public UserManage(int role, int user) {
        this.role = role;
        this.user = user;
    }
}
