package com.majesticbyte;

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
        
        return "Greetings from Spring Boot! "+userRepository.toString();
    }
}
