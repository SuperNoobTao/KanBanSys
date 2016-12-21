package cc.superliar.repo;

import cc.superliar.enums.ValidFlag;
import cc.superliar.po.Device;
import cc.superliar.po.Resource;
import cc.superliar.po.User;

import java.util.Optional;

/**
 * Created by shentao on 2016/12/20.
 */
public interface DeviceReposity extends CustomRepository<Device, String> {

    Optional<Device> findByNameAndValidFlag(String name, ValidFlag validFlag);
    Optional<Device> findByIdAndValidFlag(String name, ValidFlag validFlag);
}
