FROM openjdk:8-jdk-alpine AS BUILD_IMAGE
COPY pom.xml .
COPY src src 
RUN apk add maven
RUN mvn clean install


# Second Stage
FROM openjdk:8-jdk-alpine
ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY --from=BUILD_IMAGE target/base-api-0.0.1-SNAPSHOT.jar car-api.jar
EXPOSE 8080
USER 1000
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar ${APP_HOME}/car-api.jar"]