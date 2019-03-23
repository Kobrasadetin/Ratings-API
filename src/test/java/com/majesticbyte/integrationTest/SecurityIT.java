package com.majesticbyte.integrationTest;

import com.jayway.jsonpath.JsonPath;
import com.majesticbyte.model.AppUser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

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

        //String jwtToken = result.getResponse().getHeader("Authorization");
        String jwtToken = JsonPath.parse(result.getResponse().getContentAsString()).read("$.jwtToken");

        mvc.perform(get("/")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", jwtToken))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(new MediaType("application", "*+json")));
    }

}
