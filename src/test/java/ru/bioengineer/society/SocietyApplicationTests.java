package ru.bioengineer.society;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SocietyApplicationTests {

    @BeforeAll
    static void beforeAll() {
        System.setProperty("DB_MASTER_JDBC_URL", "jdbc:postgresql://localhost:5430/society_db");
        System.setProperty("DB_SLAVE_JDBC_URL", "jdbc:postgresql://localhost:5451/society_db");
        System.setProperty("DB_USERNAME", "master");
        System.setProperty("DB_PASSWORD", "masterpass");
        System.setProperty("DB_PATH_TO_DRIVER_CLASS", "org.postgresql.Driver");
    }

    @Test
    void contextLoads() {
    }

}
