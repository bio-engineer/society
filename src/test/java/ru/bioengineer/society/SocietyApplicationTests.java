package ru.bioengineer.society;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SocietyApplicationTests {

    @BeforeAll
    static void beforeAll() {
        System.setProperty("DB_JDBC_URL", "jdbc:postgresql://localhost:5440/db_main");
        System.setProperty("DB_USERNAME", "postgres");
        System.setProperty("DB_PASSWORD", "password");
        System.setProperty("DB_PATH_TO_DRIVER_CLASS", "org.postgresql.Driver");
    }

    @Test
    void contextLoads() {
    }

}
