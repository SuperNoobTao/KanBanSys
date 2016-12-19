package cc.superliar.repo;

import cc.superliar.enums.ValidFlag;
import cc.superliar.po.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by shentao on 2016/11/8.
 */
@Repository
public interface UserReposity extends CustomRepository<User, Long> {


    Optional<User> findByAccountAndValidFlag(String usr, ValidFlag validFlag);
}
