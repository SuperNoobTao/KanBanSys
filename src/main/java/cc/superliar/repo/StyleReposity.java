package cc.superliar.repo;

import cc.superliar.enums.ValidFlag;
import cc.superliar.po.Device;
import cc.superliar.po.Role;
import cc.superliar.po.Style;

import java.util.Optional;

/**
 * Created by shentao on 2016/12/21.
 */
public interface StyleReposity extends CustomRepository<Style, Long> {


    Optional<Device> findByTimeAndMode(String time,String mode);

}
