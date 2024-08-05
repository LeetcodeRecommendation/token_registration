package com.leetcoders.user_query_handling.beans;

import com.leetcoders.user_query_handling.utils.CassandraHandler;
import com.leetcoders.user_query_handling.utils.EnvironmentHandler;
import com.leetcoders.user_query_handling.utils.PostgresHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBeans {
    @Bean
    public PostgresHandler postgresHandler() {
        return new PostgresHandler(EnvironmentHandler.getPGServerIP(), EnvironmentHandler.getPGServerPort(),
                EnvironmentHandler.getPGUsername(),EnvironmentHandler.getPGPassword());
    }

    @Bean
    public CassandraHandler cassandraHandler() {
        return new CassandraHandler(EnvironmentHandler.getCassandraUrls());
    }
}
