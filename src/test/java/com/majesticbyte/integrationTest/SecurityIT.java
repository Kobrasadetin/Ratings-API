package com.majesticbyte.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.majesticbyte.Application;
import com.majesticbyte.model.AppUser;
import com.majesticbyte.service.AppUserService;
import org.junit.Before;
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

public class SecurityIT extends IntegrationTestTemplate{

    @Before
    public void createAdmin(){
        AppUser user = AppUser.adminWithProperties("admin","password");
        userService.createUser(user);
    }

    @Test
    public void givenUserNotLoggedInResponseIsUnauthorized() throws Exception {
        mvc.perform(get("/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenUserGivesWrongPasswordResponseIsUnauthorized() throws Exception {
        mvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"username\": \"admin\",\n" +
                        "\t\"password\": \"wrongpassword\"\n" +
                        "}"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void givenUserLogsInUserCanQueryApi() throws Exception {
        MvcResult result;

        result = mvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"username\": \"admin\",\n" +
                        "\t\"password\": \"password\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andReturn();

        String jwtToken = result.getResponse().getHeader("Authorization");

        mvc.perform(get("/")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(new MediaType("application", "*+json")));
    }

}
