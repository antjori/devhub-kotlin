# devhub-kotlin
Simple Spring Boot 2 based application that exposes a service, developed in Kotlin, allowing the encryption of data using Galois/Counter Mode (GCM).

## API

Swagger UI: http://localhost:8081/encryption/swagger-ui/index.html

## Run the application

- Debug mode:

```
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```
