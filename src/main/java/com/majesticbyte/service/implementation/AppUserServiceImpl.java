package com.majesticbyte.service.implementation;

import com.majesticbyte.model.AppUser;
import com.majesticbyte.repository.UserRepository;
import com.majesticbyte.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public AppUser createUser(AppUser userDetails) {
        String cryptedPassword = encoder.encode(userDetails.getPassword());
        userDetails.setPassword(cryptedPassword);
        return userRepository.save(userDetails);
    }

    @Override
    public void addAdminIfNoAdmins(String username, String password) {
        Iterable<AppUser> users = userRepository.findAll();
        for (AppUser user : users) {
            if (user.getRole().equals(AppUser.ROLE_ADMIN)) {
                return;
            }
        }
        createUser(AppUser.adminWithProperties(username, password));
    }
}
