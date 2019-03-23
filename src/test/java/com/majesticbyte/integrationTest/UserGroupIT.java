package com.majesticbyte.integrationTest;

import com.jayway.jsonpath.JsonPath;
import com.majesticbyte.configuration.ApplicationSettings;
import com.majesticbyte.model.AppUser;
import com.majesticbyte.model.UserGroup;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserGroupIT extends IntegrationTestTemplate {

    private String adminToken;

    private String userToken;

    @Autowired
    private ApplicationSettings applicationSettings;

    @Before
    public void initialize() throws Exception {
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
                        .contentTypeCompatibleWith(responseMediaType));
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
                        .contentTypeCompatibleWith(responseMediaType));
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
                            .contentTypeCompatibleWith(responseMediaType));
        }
        mvc.perform(post("/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(group))
                .header("Authorization", userToken))
                .andExpect(status().is4xxClientError())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void givenUserDeletesOwnGroupItIsDeleted() throws Exception {
        UserGroup group = new UserGroup();
        group.setName("test group");
        MvcResult result = mvc.perform(post("/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(group))
                .header("Authorization", userToken))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(responseMediaType))
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String restUrl = JsonPath.parse(resultContent).read("$._links.self.href");

        mvc.perform(get(restUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(group))
                .header("Authorization", userToken))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(responseMediaType));

        mvc.perform(delete(restUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(group))
                .header("Authorization", userToken))
                .andExpect(status().isNoContent());

        mvc.perform(get(restUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(group))
                .header("Authorization", userToken))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void givenUserDeletesOtherUsersGroupItIsNotDeleted() throws Exception {
        UserGroup group = new UserGroup();
        group.setName("test group");
        MvcResult result = mvc.perform(post("/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(group))
                .header("Authorization", adminToken))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(responseMediaType))
                .andReturn();

        String resultContent = result.getResponse().getContentAsString();
        String restUrl = JsonPath.parse(resultContent).read("$._links.self.href");

        mvc.perform(get(restUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(group))
                .header("Authorization", userToken))
                .andExpect(status().is4xxClientError());

        mvc.perform(delete(restUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(group))
                .header("Authorization", userToken))
                .andExpect(status().is4xxClientError());

        mvc.perform(get(restUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(group))
                .header("Authorization", userToken))
                .andExpect(status().is4xxClientError());

        mvc.perform(get(restUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(group))
                .header("Authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(responseMediaType));
    }

    @Test
    public void usersSeeOnlyOwnGroups() throws Exception {
        UserGroup group = new UserGroup();
        group.setName("test group");
        int createAmount = 3;

        mvc.perform(get("/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(group))
                .header("Authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.groups", hasSize(0)));

        for (int x=0; x<createAmount; x++) {
            mvc.perform(post("/groups")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(group))
                    .header("Authorization", adminToken))
                    .andExpect(status().isCreated());
        }

        mvc.perform(get("/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(group))
                .header("Authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.groups", hasSize(createAmount)));

        mvc.perform(get("/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(group))
                .header("Authorization", userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.groups", hasSize(0)));

    }

}
