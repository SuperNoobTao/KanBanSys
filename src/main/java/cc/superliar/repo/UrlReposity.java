package cc.superliar.repo;

import cc.superliar.enums.ValidFlag;
import cc.superliar.po.Resource;
import cc.superliar.po.Url;
import cc.superliar.po.User;

import java.util.Optional;

/**
 * Created by shentao on 2016/12/20.
 */
public interface UrlReposity extends CustomRepository<Url, Long> {


    Optional<Url> findByContentAndValidFlag(String content,ValidFlag validFlag);
}
