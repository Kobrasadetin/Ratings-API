package com.majesticbyte.service.implementation;

import com.majesticbyte.model.AppUser;
import com.majesticbyte.repository.UserRepository;
import com.majesticbyte.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public AppUser createUser(AppUser userDetails){
        String cryptedPassword = encoder.encode(userDetails.getPassword());
        userDetails.setPassword(cryptedPassword);
        return userRepository.save(userDetails);
    }
}
