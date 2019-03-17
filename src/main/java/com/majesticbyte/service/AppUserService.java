package com.majesticbyte.service;

import com.majesticbyte.model.AppUser;

public interface AppUserService {
    public AppUser createUser(AppUser userDetails);

    public void addAdminIfNoAdmins(String username, String password);
}
