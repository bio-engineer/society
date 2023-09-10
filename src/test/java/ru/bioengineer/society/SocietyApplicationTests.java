package ru.bioengineer.society;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SocietyApplicationTests {

    @BeforeAll
    static void beforeAll() {
        System.setProperty("DB_JDBC_URL", "jdbc:h2:mem:testdb");
        System.setProperty("DB_USERNAME", "sa");
        System.setProperty("DB_PASSWORD", "");
        System.setProperty("DB_PATH_TO_DRIVER_CLASS", "org.h2.Driver");
    }

    @Test
    void contextLoads() {
    }

}
