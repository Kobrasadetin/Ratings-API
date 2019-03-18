package com.majesticbyte.security.model;

import com.majesticbyte.model.AppUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class AuthenticatedUser implements UserDetails {

    private AppUser appUser;
    private boolean accountNonExpired;
    private boolean nonExpired;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;
    private boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;

    public AuthenticatedUser(AppUser appUser,
                             Collection<? extends GrantedAuthority> authorities) {
        this.appUser = appUser;
        this.authorities = authorities;
        this.setAccountNonExpired(true);
        this.setAccountNonLocked(true);
        this.setCredentialsNonExpired(true);
        this.setEnabled(true);
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getUsername();
    }

}
