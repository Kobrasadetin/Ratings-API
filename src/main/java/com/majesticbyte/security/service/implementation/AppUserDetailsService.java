package com.majesticbyte.security.service.implementation;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AppUserDetailsService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
