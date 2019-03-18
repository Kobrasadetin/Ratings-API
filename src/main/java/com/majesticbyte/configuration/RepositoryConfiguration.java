package com.majesticbyte.configuration;

import com.majesticbyte.events.GroupEventHandler;
import com.majesticbyte.events.UserEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration{

    public RepositoryConfiguration(){
        super();
    }

    @Bean
    UserEventHandler userEventHandler() {
        return new UserEventHandler();
    }

    @Bean
    GroupEventHandler groupEventHandler() {
        return new GroupEventHandler();
    }

}