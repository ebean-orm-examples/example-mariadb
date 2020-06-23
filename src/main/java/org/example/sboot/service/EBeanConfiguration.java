package org.example.sboot.service;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.config.MatchingNamingConvention;
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
        config.setName("ebeanServer");
        config.setNamingConvention(new MatchingNamingConvention());
        config.setDefaultServer(true);
        config.setDataSource(dataSource);
        config.setCurrentUserProvider(currentUser);
        //config.setDdlInitSql("schema.sql");
        config.setDdlGenerate(true);
        config.setDdlRun(true);
        config.setDataTimeZone("UTC");

        //config.addPackage("com.clevergang.dbtests.repository.impl.ebean.entities");
        //config.setExternalTransactionManager(new SpringJdbcTransactionManager());
        //config.setExpressionNativeIlike(true);
        //config.loadFromProperties();
        return config;
    }

    @Bean
    public Database ebeanDatabase(DatabaseConfig databaseConfig) {

        return DatabaseFactory.create(databaseConfig);
    }
}

