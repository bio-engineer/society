FROM openjdk:20-slim

ADD ./target/society.jar /app/

CMD ["java", "-jar", "/app/society.jar"]

EXPOSE 8080