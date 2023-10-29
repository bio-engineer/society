package ru.bioengineer.society.infrastructure.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.MapDataSourceLookup;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
public class AppConfig {

    @Bean
    DataSource masterDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(getRequiredEnv("DB_PATH_TO_DRIVER_CLASS"));
        config.setUsername(getRequiredEnv("DB_USERNAME"));
        config.setPassword(getRequiredEnv("DB_PASSWORD"));
        config.setJdbcUrl(getRequiredEnv("DB_MASTER_JDBC_URL"));
        return new HikariDataSource(config);
    }

    @Bean
    DataSource slaveDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(getRequiredEnv("DB_PATH_TO_DRIVER_CLASS"));
        config.setUsername(getRequiredEnv("DB_USERNAME"));
        config.setPassword(getRequiredEnv("DB_PASSWORD"));
        config.setJdbcUrl(getRequiredEnv("DB_SLAVE_JDBC_URL"));
        return new HikariDataSource(config);
    }

    @Bean
    @Primary
    DataSource dataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                          @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        Map<String, DataSource> lookupDataSources = new HashMap<>();
        lookupDataSources.put("master", masterDataSource);
        lookupDataSources.put("slave", slaveDataSource);
        DataSourceLookup dataSourceLookup = new MapDataSourceLookup(lookupDataSources);

        Map<Object, Object> map = new HashMap<>();
        map.put("master", masterDataSource);
        map.put("slave", slaveDataSource);

        RoutingDataSource routing = new RoutingDataSource();
        routing.setDataSourceLookup(dataSourceLookup);
        routing.setTargetDataSources(map);
        return routing;
    }

    private static String getRequiredEnv(String name) {
        String env = Optional.ofNullable(System.getenv(name))
                .orElseGet(() -> System.getProperty(name));
        if (env == null) {
            throw new IllegalStateException("Environment [" + name + "] not found");
        }
        return env;
    }

}
