package com.majesticbyte.events;

import com.majesticbyte.model.AppUser;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.prepost.PreAuthorize;

import java.security.Principal;
import java.util.logging.Logger;

@RepositoryEventHandler
public class UserEventHandler {
    Logger logger = Logger.getLogger("Class AppUserEventHandler");

    public UserEventHandler() {
        super();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @HandleBeforeCreate
    public void handleAppUserBeforeCreate(AppUser appUser) {
        //logger.info("handleAppUserBeforeCreate: "+principal.toString());
    }
}
