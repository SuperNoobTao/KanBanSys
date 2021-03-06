package cc.superliar.conf.custom;

import cc.superliar.enums.ValidFlag;
import cc.superliar.po.User;
import cc.superliar.repo.UserReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by shentao on 2016/11/17.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserReposity userReposity;

    @Override
    public UserDetails loadUserByUsername(String usr) throws UsernameNotFoundException {
        User user = userReposity.findByAccountAndValidFlag(usr, ValidFlag.VALID).orElseThrow(
                // Throw cannot find any user by this usr param.
                () -> new UsernameNotFoundException(String.format("UserManageVO %s does not exist!", usr)));
        return new CustomUserRepositoryUserDetails(user);

    }
}
