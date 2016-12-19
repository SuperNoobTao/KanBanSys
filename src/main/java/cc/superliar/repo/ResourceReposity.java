package cc.superliar.repo;

import cc.superliar.enums.ValidFlag;
import cc.superliar.po.Resource;

import java.util.Optional;

/**
 * Created by shentao on 2016/11/29.
 */
public interface ResourceReposity extends CustomRepository<Resource, Long> {

    Optional<Resource> findByNameAndValidFlag(String name, ValidFlag validFlag);

}
