package com.majesticbyte.service;

import com.majesticbyte.model.AppUser;

public interface AppUserService {

    AppUser createUser(AppUser userDetails);

    void addAdminIfNoAdmins(String username, String password);

    AppUser getAuthenticatedUser();

    boolean groupLimitReached(AppUser user);

}
