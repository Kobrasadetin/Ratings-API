package com.majesticbyte.service.implementation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.majesticbyte.model.AppUser;
import com.majesticbyte.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService  {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // hard coding the users. All passwords must be encoded.
        Optional<AppUser> appUser = userRepository.findOneByUsername(username);

        if(appUser.isPresent()) {

            // Remember that Spring needs roles to be in this format: "ROLE_" + userRole (i.e. "ROLE_ADMIN")
            // So, we need to set it to that format, so we can verify and compare roles (i.e. hasRole("ADMIN")).
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                    .commaSeparatedStringToAuthorityList("ROLE_" + appUser.get().getRole());

            // The "User" class is provided by Spring and represents a model class for user to be returned by UserDetailsService
            // And used by auth manager to verify and check user authentication.
            return new User(appUser.get().getUsername(), appUser.get().getPassword(), grantedAuthorities);
        }

        // If user not found. Throw this exception.
        throw new UsernameNotFoundException("Username: " + username + " not found");
    }

}