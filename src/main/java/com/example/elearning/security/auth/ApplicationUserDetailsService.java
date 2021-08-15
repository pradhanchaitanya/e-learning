package com.example.elearning.security.auth;

import com.example.elearning.exception.ResourceNotFoundException;
import com.example.elearning.model.User;
import com.example.elearning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        ApplicationAuthUser authUser = null;
        try {
            user = userService.findByUsername(username);
            authUser = new ApplicationAuthUser(user);
        } catch (ResourceNotFoundException e) { }

        return authUser;
    }
}
