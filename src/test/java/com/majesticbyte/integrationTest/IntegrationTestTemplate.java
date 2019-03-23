package com.majesticbyte.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.majesticbyte.Application;
import com.majesticbyte.model.AppUser;
import com.majesticbyte.service.AppUserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(
        classes = Application.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
//We use DirtiesContext instead of Transactional tests because the latter causes ambiguity between
//attached/detached entities, and we currently don't care about test performance.
//See: https://www.javacodegeeks.com/2011/12/spring-pitfalls-transactional-tests.html
@AutoConfigureMockMvc
public abstract class IntegrationTestTemplate {

    @Autowired
    protected AppUserService userService;

    @Autowired
    protected MockMvc mvc;

    @Autowired
    protected BCryptPasswordEncoder passwordEncoder;

    protected static MediaType responseMediaType = new MediaType("application", "*+json");

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String authenticate(AppUser user) throws Exception {
        MvcResult result = mvc.perform(post("/auth")
                .param("jwt-in-header", "true")
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
