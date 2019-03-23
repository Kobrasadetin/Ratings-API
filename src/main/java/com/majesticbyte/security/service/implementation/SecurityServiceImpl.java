package com.majesticbyte.security.service.implementation;

import com.majesticbyte.model.AppUser;
import com.majesticbyte.model.UserGroup;
import com.majesticbyte.security.SecurityService;
import com.majesticbyte.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("securityService")
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private AppUserService appUserService;

    @Override
    public boolean canViewGroup() {
        return false;
    }

    @Override
    public boolean canViewGroup(Long id) {
        AppUser authenticatedUser = appUserService.getAuthenticatedUser();
        if (appUserService.userIsAdmin(authenticatedUser)) return true;
        if (authenticatedUser.getGroups().stream()
                .anyMatch(group -> group.getId().equals(id))){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean canViewGroups(UserGroup userGroup) {
        AppUser authenticatedUser = appUserService.getAuthenticatedUser();
        if (appUserService.userIsAdmin(authenticatedUser)) return true;
        return authenticatedUser.getGroups().contains(userGroup);
    }
}

