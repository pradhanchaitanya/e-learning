package com.example.elearning.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.example.elearning.security.auth.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService applicationUserService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole(ADMIN.name())
                .antMatchers("/home/**")
                    .access("hasRole('ROLE_STUDENT') or hasRole('ROLE_TEACHER')")
                .antMatchers("/chat/**")
                    .access("hasRole('ROLE_STUDENT') or hasRole('ROLE_TEACHER')")
                .antMatchers("/login", "/signup")
                    .permitAll()
                .and()
                .formLogin()
                    .successHandler(
                            (httpServletRequest, httpServletResponse, authentication) -> {
                                try {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("message", authentication.isAuthenticated());

                                    httpServletResponse.getOutputStream()
                                            .println(
                                                    mapper.writeValueAsString(data)
                                            );

                                    handleRequest(httpServletRequest, httpServletResponse, authentication);
                                } catch (Exception e) {}
                            }
                    )
                    .failureUrl("/login?error=true")
                        .failureHandler(
                                (httpServletRequest, httpServletResponse, authenticationException) -> {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("message", Boolean.FALSE);
                                    data.put("exception", authenticationException.getMessage());

                                    httpServletResponse.getOutputStream()
                                            .println(
                                                    mapper.writeValueAsString(data)
                                            );
                                }
                        )
                    .and()
                    .logout()
                        .logoutSuccessHandler(
                                (httpServletRequest, httpServletResponse, authentication) -> {
                                    try {
                                        Map<String, Object> data = new HashMap<>();
                                        data.put("message", authentication.isAuthenticated());

                                        httpServletResponse.getOutputStream()
                                                .println(
                                                        mapper.writeValueAsString(data)
                                                );

                                        httpServletResponse.sendRedirect("/login");
                                    } catch (Exception e) {}
                                }
                        )
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(getPasswordEncoder());
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void handleRequest(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            return;
        }

        response.sendRedirect(targetUrl);
    }

    protected String determineTargetUrl(final Authentication authentication) {
        Map<String, String> roleMap = new HashMap<>();
        roleMap.put("ROLE_STUDENT", "/home");
        roleMap.put("ROLE_TEACHER", "/home");
        roleMap.put("ROLE_ADMIN", "/admin");

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (roleMap.containsKey(authorityName)) {
                return roleMap.get(authorityName);
            }
        }

        throw new IllegalStateException();
    }
}
