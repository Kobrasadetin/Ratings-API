package com.majesticbyte.events;

import com.majesticbyte.exceptions.GroupLimitReachedException;
import com.majesticbyte.model.AppUser;
import com.majesticbyte.model.UserGroup;
import com.majesticbyte.repository.UserGroupRepository;
import com.majesticbyte.repository.UserRepository;
import com.majesticbyte.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Set;
import java.util.logging.Logger;

@RepositoryEventHandler
public class GroupEventHandler {
    Logger logger = Logger.getLogger("Class GroupEventHandler");

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private UserGroupRepository groupRepository;

    @Autowired
    private EntityManager entityManager;

    public GroupEventHandler() {
        super();
    }

    @HandleBeforeCreate
    public void handleGroupBeforeCreate(UserGroup userGroup) throws GroupLimitReachedException {
        AppUser authenticatedUser = appUserService.getAuthenticatedUser();
        if (appUserService.groupLimitReached(authenticatedUser)) {
            throw new GroupLimitReachedException("group limit reached");
        }
        logger.info("handleGroupBeforeCreate: " + userGroup.toString());
    }
    @HandleAfterCreate
    public void handleGroupAfterCreate(UserGroup userGroup) throws GroupLimitReachedException {
        AppUser authenticatedUser = appUserService.getAuthenticatedUser();

        addUserIfNotPresent(authenticatedUser, userGroup);
        appUserService.save(authenticatedUser);
        logger.info("handleGroupAfterCreate: " + userGroup.toString());
        logger.info("security name: " + authenticatedUser.getUsername());
    }


    private void addUserIfNotPresent(AppUser authenticatedUser, UserGroup userGroup) {
        authenticatedUser.getGroups().add(userGroup);
        authenticatedUser.getAdminInGroups().add(userGroup);
    }

}
