package cc.superliar.component;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 加密类
 * Created by shentao on 2016/11/17.
 */
@Component
public class CustomPasswordEncoder implements PasswordEncoder {
    /**
     * 加密
     * @param rawPassword
     * @return
     */
    @Override
    public String encode(CharSequence rawPassword) {
        String rawPwd = (String) rawPassword;
        return BCrypt.hashpw(rawPwd, BCrypt.gensalt());
    }

    /**
     * 匹配
     * @param rawPassword
     * @param encodedPassword
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String rawPwd = (String) rawPassword;
        return BCrypt.checkpw(rawPwd, encodedPassword);
    }
}
