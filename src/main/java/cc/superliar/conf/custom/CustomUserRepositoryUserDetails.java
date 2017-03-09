package cc.superliar.conf.custom;

import cc.superliar.po.Resource;
import cc.superliar.po.Role;
import cc.superliar.po.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Created by shentao on 2016/11/17.
 */

public class CustomUserRepositoryUserDetails extends User implements UserDetails {

    private static final long serialVersionUID = -2502869413772228006L;

    public CustomUserRepositoryUserDetails(User user) {
        super(user);
    }

    /**
     * 得到权限
     *
     * @return GrantedAuthorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Set<Role> roles = getRoles();
        for (Role role : roles) {
                    GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                    authorities.add(authority);
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return getPwd();
    }

    @Override
    public String getUsername() {
        return getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
