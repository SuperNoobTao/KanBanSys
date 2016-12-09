package cc.superliar.domain;

import cc.superliar.po.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by shentao on 2016/11/29.
 */
@Service
@Transactional(readOnly = true)
public class UserDomain extends BaseDomain<User,Long> {


}
