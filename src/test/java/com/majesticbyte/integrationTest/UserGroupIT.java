package com.majesticbyte.integrationTest;

import com.majesticbyte.configuration.ApplicationSettings;
import com.majesticbyte.model.AppUser;
import com.majesticbyte.model.UserGroup;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserGroupIT extends IntegrationTestTemplate {

    private String adminToken;

    private String userToken;

    @Autowired
    private ApplicationSettings applicationSettings;

    @Before
    public void initialize() throws Exception{
        AppUser user = AppUser.userWithProperties("user", "password");
        AppUser admin = AppUser.adminWithProperties("admin", "password");
        userService.createUser(user);
        userService.createUser(admin);
        userToken = authenticate(user);
        adminToken = authenticate(admin);
    }

    @Test
    public void givenUserLogsInUserCanCreateGroup() throws Exception {
        UserGroup group = new UserGroup();
        group.setName("test group");
        mvc.perform(post("/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(group))
                .header("Authorization", userToken))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(new MediaType("application", "*+json")));
    }

    @Test
    public void givenAdminLogsInAdminCanCreateGroup() throws Exception {
        UserGroup group = new UserGroup();
        group.setName("test group");
        mvc.perform(post("/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(group))
                .header("Authorization", adminToken))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(new MediaType("application", "*+json")));
    }

    @Test
    public void givenUserAddsTooManyGroupsReceivesError() throws Exception {
        UserGroup group = new UserGroup();
        group.setName("test group");
        for (int counter = 0; counter < (applicationSettings.getGroupLimit()); counter++) {
            mvc.perform(post("/groups")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(group))
                    .header("Authorization", userToken))
                    .andExpect(status().isCreated())
                    .andExpect(content()
                            .contentTypeCompatibleWith(new MediaType("application", "*+json")));
        }
        mvc.perform(post("/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(group))
                .header("Authorization", userToken))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}
