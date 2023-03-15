FROM openjdk:8-jdk-alpine
ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY . .
RUN apk add maven
RUN mvn clean install
ARG JAR_FILE=target/base-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
USER 1000
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar ${APP_HOME}/app.jar"]