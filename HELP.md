# Getting Started

## Helpful commands

#### (Run in the terminal)

### To run the application:

```
./mvnw spring-boot:run
```
or 
if you want to run another instance of the same microservice but in another port:

```
./mvnw spring-boot:run -D"spring-boot.run.arguments"="--server.port=9080"
```

### To run all the tests:

```
./mvnw test
```

### To run all tests in a particular class:

```
./mvnw -Dtest=ChallengeServiceTest test
```

## Testing using HTTPie

Make sure the application is running.

### Get random challenge

```
http -b :8080/challenges/random
```

### Post attempt to solve a challenge

```
 http POST :8080/attempts factorA=58 factorB=92 userAlias=foy answer=5400
```

## H2 Database web console

if the configuration in application.properties is in place you can access it through:

```
http://localhost:8080/h2-console
```

use the following as JDBC URL

```
jdbc:h2:mem:testdb
```

## Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.3/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.3/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.3/reference/htmlsingle/index.html#web)
* [Validation](https://docs.spring.io/spring-boot/docs/3.2.3/reference/htmlsingle/index.html#io.validation)

## Guides

The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)

