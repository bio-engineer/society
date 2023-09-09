package ru.bioengineer.society.infrastructure.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Optional;

@Configuration
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(getRequiredEnv("DB_PATH_TO_DRIVER_CLASS"));
        config.setUsername(getRequiredEnv("DB_USERNAME"));
        config.setPassword(getRequiredEnv("DB_PASSWORD"));
        config.setJdbcUrl(getRequiredEnv("DB_JDBC_URL"));
        return new HikariDataSource(config);
    }

    private static String getRequiredEnv(String name) {
        String env = Optional.ofNullable(System.getenv(name))
                .orElseGet(() -> System.getProperty(name));
        if (Strings.isEmpty(env)) {
            throw new IllegalStateException("Environment [" + name + "] not found");
        }
        return env;
    }

}
