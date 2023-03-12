FROM openjdk:8-jdk-alpine
ENV APP_HOME=/root/dev/app
WORKDIR $APP_HOME
COPY . .
RUN apk add maven
RUN mvn clean install
ARG JAR_FILE=target/base-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /root/dev/app/app.jar"]