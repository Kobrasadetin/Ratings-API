package com.majesticbyte.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class ApplicationSettings {

    @Value("${ratings-api.settings.user-group-limit:10}")
    private Integer groupLimit;

    @Value("${ratings-api.settings.group-match-limit:2048}")
    private Integer matchLimit;

}
