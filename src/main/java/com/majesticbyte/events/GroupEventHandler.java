package com.majesticbyte.events;

import com.majesticbyte.exceptions.GroupLimitReachedException;
import com.majesticbyte.model.AppUser;
import com.majesticbyte.model.UserGroup;
import com.majesticbyte.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import java.util.Collections;
import java.util.logging.Logger;

@RepositoryEventHandler
public class GroupEventHandler {
    Logger logger = Logger.getLogger("Class GroupEventHandler");

    @Autowired
    private AppUserService appUserService;

    public GroupEventHandler() {
        super();
    }

    @HandleBeforeCreate
    public void handleGroupBeforeCreate(UserGroup userGroup) throws GroupLimitReachedException{
        AppUser authenticatedUser = appUserService.getAuthenticatedUser();
        if (appUserService.groupLimitReached(authenticatedUser)){
            throw new GroupLimitReachedException("group limit reached");
        }
        userGroup.getAdmins().add(authenticatedUser);
        userGroup.getMembers().add(authenticatedUser);
        logger.info("handleGroupBeforeCreate: "+userGroup.toString());
        logger.info("security name: "+ authenticatedUser.getUsername());
    }

}
