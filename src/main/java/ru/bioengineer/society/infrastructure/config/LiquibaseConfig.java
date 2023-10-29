package ru.bioengineer.society.infrastructure.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    @Bean
    SpringLiquibase liquibase(@Qualifier("masterDataSource") DataSource masterDataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(masterDataSource);
        liquibase.setChangeLog("db/changelog/changelog.xml");
        return liquibase;
    }

}
