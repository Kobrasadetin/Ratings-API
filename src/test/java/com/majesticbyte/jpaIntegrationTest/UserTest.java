package com.majesticbyte.jpaIntegrationTest;

import com.majesticbyte.model.AppUser;
import com.majesticbyte.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void userCreationTest() throws Exception{
        AppUser newUser = new AppUser();
        newUser.setUsername("TestUser");
        assertNotEquals(java.util.Optional.of(1L), newUser.getId());
        AppUser savedUser = userRepository.save(newUser);
        List<AppUser> usersInrepo = userRepository.findByUsername("TestUser");

        assertEquals("TestUser", usersInrepo.get(0).getUsername());
        assertEquals(savedUser, usersInrepo.get(0));
    }

    @Test
    public void findAllUsersTest() {
        AppUser customer1 = AppUser.adminWithProperties("Adam", "user1");
        entityManager.persist(customer1);

        AppUser customer2 = AppUser.adminWithProperties("Abel", "user2");
        entityManager.persist(customer2);

        AppUser customer3 = AppUser.adminWithProperties("Cain", "user3");
        entityManager.persist(customer3);

        Iterable<AppUser> customers = userRepository.findAll();

        assertThat(customers).hasSize(3).contains(customer1, customer2, customer3);
    }

}
