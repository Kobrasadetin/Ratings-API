package com.majesticbyte.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.majesticbyte.Application;
import com.majesticbyte.model.AppUser;
import com.majesticbyte.service.AppUserService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(
        classes = Application.class)
@Transactional
@AutoConfigureMockMvc
public class UserGroupIT {

    @Autowired
    private AppUserService userService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    private String adminToken;

    private String userToken;

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
        mvc.perform(post("/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", userToken))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(new MediaType("application", "*+json")));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String authenticate(AppUser user) throws Exception {
        MvcResult result = mvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"username\": \"" + user.getUsername() + "\",\n" +
                        "\t\"password\": \"password\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andReturn();

        return result.getResponse().getHeader("Authorization");
    }

}
