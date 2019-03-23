package com.majesticbyte.events;

import com.majesticbyte.exceptions.ExceptionMessage;
import com.majesticbyte.exceptions.GroupLimitReachedException;
import com.majesticbyte.exceptions.UnauthorizedOperationException;
import com.majesticbyte.model.AppUser;
import com.majesticbyte.model.UserGroup;
import com.majesticbyte.repository.UserGroupRepository;
import com.majesticbyte.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
            throw new GroupLimitReachedException(new ExceptionMessage("group limit reached"));
        }
        setCreatorCredentials(authenticatedUser, userGroup);
        logger.info("user "+authenticatedUser.getUsername()+ " created group " + userGroup.getName());
    }

    @HandleBeforeDelete
    public void handleGroupBeforeDelete(UserGroup userGroup) throws UnauthorizedOperationException {
        AppUser authenticatedUser = appUserService.getAuthenticatedUser();
        if (!userGroup.getAdmins().contains(authenticatedUser)) {
            throw new UnauthorizedOperationException();
        }
        logger.info("user "+authenticatedUser.getUsername()+ " deleted group " + userGroup.getName());
    }


    private void setCreatorCredentials(AppUser authenticatedUser, UserGroup userGroup) {
        userGroup.setCreator(authenticatedUser);
        userGroup.getMembers().add(authenticatedUser);
        userGroup.getAdmins().add(authenticatedUser);
        //authenticatedUser.getCreatedGroups().add(userGroup);
        //authenticatedUser.getGroups().add(userGroup);
        //authenticatedUser.getAdminInGroups().add(userGroup);
    }

}
