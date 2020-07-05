package org.example.sboot.service;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.config.MatchingNamingConvention;
import io.ebean.config.ServerConfig;
import org.example.sboot.domain.Contract;
import org.example.sboot.domain.Payment;
import org.example.sboot.domain.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class EBeanConfiguration {

    @Autowired
    CurrentUser currentUser;

    @Bean
    public DatabaseConfig ebeanServerConfig(DataSource dataSource) {
        DatabaseConfig config = new DatabaseConfig();
        config.setDataSource(dataSource);
        config.setCurrentUserProvider(currentUser);
        config.setDataTimeZone("UTC");
        config.loadFromProperties();
        return config;
    }

    @Bean
    public Database ebeanDatabase(DatabaseConfig databaseConfig) {
        return DatabaseFactory.create(databaseConfig);
    }
}

