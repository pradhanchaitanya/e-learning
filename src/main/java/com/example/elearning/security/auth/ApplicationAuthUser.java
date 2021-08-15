package com.example.elearning.security.auth;

import com.example.elearning.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class ApplicationAuthUser implements UserDetails {

    private final String username;
    private final String password;
    private final Set<? extends GrantedAuthority> grantedAuthorities;
    private final boolean isAccountNonExpired;
    private final boolean isAccountNonLocked;
    private final boolean isCredentialsNonExpired;
    private final boolean isEnabled;

    public ApplicationAuthUser(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();

        if (user.isActive()) {
            this.isEnabled = true;
            this.isAccountNonLocked = true;
            this.isAccountNonExpired = true;
            this.isCredentialsNonExpired = true;
        } else {
            this.isEnabled = false;
            this.isAccountNonLocked = false;
            this.isAccountNonExpired = false;
            this.isCredentialsNonExpired = false;
        }

        this.grantedAuthorities = ApplicationUserRole
                                            .valueOf(user.getRole())
                                            .getGrantedAuthorities();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
