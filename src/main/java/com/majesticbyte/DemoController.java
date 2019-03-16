package com.majesticbyte;

import com.majesticbyte.model.User;
import com.majesticbyte.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class DemoController {

    @NonNull
    UserRepository userRepository;

    @RequestMapping("/")
    public String index() {
        User newUser = new User();
        newUser.setUsername("TestUser");
        newUser = userRepository.save(newUser);
        User userInrepo = userRepository.findById(1L).orElseThrow();
        return "Greetings from Spring Boot! "+ newUser.toString() + " :: " +userInrepo.toString();
    }
}
