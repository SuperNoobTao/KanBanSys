package cc.superliar.conf;

import cc.superliar.conf.custom.AccessHandler;
import cc.superliar.conf.custom.CustomAuthenticationProvider;
import cc.superliar.constant.ResourceURL;
import cc.superliar.constant.VersionConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by shentao on 2016/11/17.
 */
@Configuration
@EnableWebSecurity
@EnableSpringDataWebSupport
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api", "/api/**", "/bin", "/bin/**", "/css", "/css/**", "/html", "/html/**", "/libs", "/libs/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login","/register").permitAll()
                .antMatchers(WELCOME_URL).authenticated()
                .antMatchers(CLIENT_URL).hasAnyAuthority("admin", "guest")
                .antMatchers(USER_URL).hasAnyAuthority("admin", "guest")
                .antMatchers(ROLE_URL).hasAnyAuthority("admin", "guest")
                .antMatchers(GROUP_URL).hasAnyAuthority("admin", "guest")
                .antMatchers(RESOURCE_URL).hasAnyAuthority("admin", "guest")
                .antMatchers(DEVICE_URL).hasAnyAuthority("admin", "guest")
                .antMatchers(STYLE_URL).hasAnyAuthority("admin", "guest")
                .antMatchers(URL_URL).hasAnyAuthority("admin", "guest")
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("account")
                .passwordParameter("pwd")
                .successHandler(new AccessHandler())
                .failureHandler(new AccessHandler()).permitAll().and().logout()
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .deleteCookies("remember-me")
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .rememberMe();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    private static final String RESOURCE_ID = "api";
    private static final String WELCOME_URL = getURL(ResourceURL.WELCOME);
    private static final String CLIENT_URL = getURL(ResourceURL.CLIENTS);
    private static final String USER_URL = getURL(ResourceURL.USERS);
    private static final String ROLE_URL = getURL(ResourceURL.ROLES);
    private static final String GROUP_URL = getURL(ResourceURL.GROUPS);
    private static final String RESOURCE_URL = getURL(ResourceURL.RESOURCES);
    private static final String DEVICE_URL = getURL(ResourceURL.DEVICES);
    private static final String STYLE_URL = getURL(ResourceURL.STYLES);
    private static final String URL_URL = getURL(ResourceURL.URLS);



    private static String getURL(CharSequence element) {
        return String.join("", ResourceURL.FIX, ResourceURL.RESOURCES, VersionConstant.V1, element, ResourceURL.FIX);
    }

}
