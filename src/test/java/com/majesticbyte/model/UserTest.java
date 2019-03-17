package com.majesticbyte.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class UserTest {

    @Test
    public void userCreationTest() throws Exception{
        AppUser newUser = new AppUser();
        newUser.setUsername("TestUser");
        assertEquals("TestUser", newUser.getUsername());
    }

}
