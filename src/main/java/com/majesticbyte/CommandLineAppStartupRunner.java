package com.majesticbyte;

import com.majesticbyte.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    @Autowired
    AppUserService appUserService;

    @Override
    public void run(String...args) throws Exception {
        appUserService.addAdminIfNoAdmins("admin", "admin");
    }
}