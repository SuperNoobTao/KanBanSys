package cc.superliar.repo;

import cc.superliar.po.UserManage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by shentao on 2017/3/9.
 */
public interface UserManageReposity extends CustomRepository<UserManage, Integer>{

    @Query(value = "delete from tb_user_has_role where user_id=?1 ", nativeQuery = true)
    @Modifying
    public void deleteManageById(Long id);
}
