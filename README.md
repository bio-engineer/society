## society

#### Учебный проект социальной сети, созданный для прохождения курса.

### Требования

- Apache Maven 3.6.3 (для сборки основного приложения)
- Java 17 (для сборки и запуска основного приложения)
- docker-compose (для запуска БД и/или альтернативного запуска основного приложения)

### Сборка проекта

```bash
mvn clean package
```

### Запуск в Docker

Следующая команда позволяет запустить БД и основное приложение в Docker:

```bash
docker-compose -f ./docker-compose.yml -p society up -d --build postgresql_main society
```

### Обычный запуск

Перед запуском убедитесь, что у вас есть запущенная БД postgresql. Также необходимо поправить значение переменных
DB_JDBC_URL, DB_USERNAME, DB_PASSWORD на актуальные.

Следующая команда позволяет запустить локально основное приложение:

```bash
export DB_JDBC_URL=jdbc:postgresql://localhost:5440/db_main
export DB_USERNAME=postgres
export DB_PASSWORD=password

export DB_PATH_TO_DRIVER_CLASS=org.postgresql.Driver

java -jar ./target/society.jar
```