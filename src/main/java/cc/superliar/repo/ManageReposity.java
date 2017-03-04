package cc.superliar.repo;

import cc.superliar.po.Device;
import cc.superliar.po.Manage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by shentao on 2016/12/26.
 */
public interface ManageReposity extends CustomRepository<Manage, Integer> {


    @Query(value = "delete from tb_device_has_url where device_id=?1 ", nativeQuery = true)
    @Modifying
    public void deleteManageById(String id);


}
