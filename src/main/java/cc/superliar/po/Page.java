package cc.superliar.po;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * Created by shentao on 2017/3/2.
 */
public class Page  implements GrantedAuthority, Serializable {
    @Override
    public String getAuthority() {
        return null;
    }
}
