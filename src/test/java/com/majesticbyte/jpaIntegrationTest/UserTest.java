package com.majesticbyte.jpaIntegrationTest;

import com.majesticbyte.model.User;
import com.majesticbyte.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void userCreationTest() throws Exception{
        User newUser = new User();
        newUser.setUsername("TestUser");
        assertNotEquals(java.util.Optional.of(1L), newUser.getId());
        User savedUser = userRepository.save(newUser);
        List<User> usersInrepo = userRepository.findByUsername("TestUser");

        assertEquals("TestUser", usersInrepo.get(0).getUsername());
        assertEquals(savedUser, usersInrepo.get(0));

    }

}
