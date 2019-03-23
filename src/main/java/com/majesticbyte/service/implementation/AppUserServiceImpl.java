package com.majesticbyte.service.implementation;

import com.majesticbyte.configuration.ApplicationSettings;
import com.majesticbyte.model.AppUser;
import com.majesticbyte.repository.UserRepository;
import com.majesticbyte.service.AppUserService;
import com.majesticbyte.util.SecurityContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService {

    public static int USER_GROUP_LIMIT = 10;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private ApplicationSettings applicationSettings;

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
            if (userIsAdmin(user)) {
                return;
            }
        }
        createUser(AppUser.adminWithProperties(username, password));
    }

    @Override
    public AppUser getAuthenticatedUser() {
        return userRepository.findOneByUsername(SecurityContextUtil.username()).orElseThrow();
    }

    @Override
    public boolean groupLimitReached(AppUser user) {
        return user.getGroups().size() >= applicationSettings.getGroupLimit();
    }

    @Override
    public AppUser save(AppUser appUser) {
        return userRepository.save(appUser);
    }

    @Override
    public boolean userIsAdmin(AppUser authenticatedUser) {
        return authenticatedUser.getRole().equals(AppUser.ROLE_ADMIN);
    }
}
