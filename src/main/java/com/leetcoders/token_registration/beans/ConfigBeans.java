package com.leetcoders.token_registration.beans;

import com.leetcoders.token_registration.utils.EnvironmentHandler;
import com.leetcoders.token_registration.utils.PostgresHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBeans {
    @Bean
    public PostgresHandler postgresHandler() {
        return new PostgresHandler(EnvironmentHandler.getPGServerIP(), EnvironmentHandler.getPGServerPort(),
                EnvironmentHandler.getPGUsername(),EnvironmentHandler.getPGPassword());
    }
}
