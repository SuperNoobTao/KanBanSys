package cc.superliar.conf.custom;

import cc.superliar.component.CustomPasswordEncoder;
import cc.superliar.enums.ValidFlag;
import cc.superliar.po.User;
import cc.superliar.repo.UserReposity;
import cc.superliar.util.SpringSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by shentao on 2016/11/17.
 */
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserReposity userReposity;
    @Autowired
    CustomPasswordEncoder customPasswordEncoder;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token  = (UsernamePasswordAuthenticationToken) authentication;
        // Find
        // user.
        String username = token.getName();
        User user = userReposity.findByAccountAndValidFlag(username, ValidFlag.VALID).orElseThrow(
                // Throw cannot find any user by this usr param.
                () -> new UsernameNotFoundException(String.format("UserManageVO %s does not exist!", username)));
        if (!customPasswordEncoder.matches(token.getCredentials().toString(), user.getPwd())) {
            throw new BadCredentialsException("Invalid username/password");
        }
        CustomUserRepositoryUserDetails userDetails = new CustomUserRepositoryUserDetails(user);

        // Get client ip address.
        String ip = SpringSecurityUtils.getCurrentUserIp();

        // Save user login info.
        user.setIp(ip);
        user.setLastLoginTime(LocalDateTime.now());
        userReposity.save(user);
        return new UsernamePasswordAuthenticationToken(userDetails, user.getPwd(), userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
