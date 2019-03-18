package com.majesticbyte.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.majesticbyte.Application;
import com.majesticbyte.model.AppUser;
import com.majesticbyte.service.AppUserService;
import org.junit.Ignore;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(
        classes = Application.class)
@Transactional
@AutoConfigureMockMvc
public abstract class IntegrationTestTemplate {

    @Autowired
    protected AppUserService userService;

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected BCryptPasswordEncoder passwordEncoder;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String authenticate(AppUser user) throws Exception {
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
