# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.app.base-api' is invalid and this project uses 'com.app.baseapi' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.3.RELEASE/maven-plugin/)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.2.3.BUILD-SNAPSHOT/reference/htmlsingle/#using-boot-devtools)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.2.3.BUILD-SNAPSHOT/reference/htmlsingle/#boot-features-jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.2.3.BUILD-SNAPSHOT/reference/htmlsingle/#boot-features-developing-web-applications)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

## for running this as a container
* from the root project directory there should be a Dockerfile
* run `docker build --tag car-api .
* run  `docker run -itd --name car-api  -p 8080:8080 -e "JAVA_OPTS= -Xms128m -Xmx512m -XX:+UseParallelGC" car-api`