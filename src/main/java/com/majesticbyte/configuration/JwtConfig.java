package com.majesticbyte.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class JwtConfig {
    @Value("${ratings-api.security.jwt.uri:/auth/**}")
    private String Uri;

    @Value("${ratings-api.security.jwt.json-key:jwtToken}")
    private String jsonKey;

    @Value("${ratings-api.security.jwt.header:Authorization}")
    private String header;

    @Value("${ratings-api.security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${ratings-api.security.jwt.expiration:#{24*60*60}}")
    private int expiration;

    //No placeholder, so that production doesn't use it accidentally
    @Value("${ratings-api.security.jwt.secret}")
    private String secret;
}