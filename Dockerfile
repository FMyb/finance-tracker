FROM openjdk:11-jdk-oracle

ENV DB_HOST test
ENV DB_NAME test
ENV DB_USERNAME test
ENV DB_PASSWORD test
ENV SERVER_PORT 9090
ENV BOT_NAME test
ENV BOT_TOKENT test

ARG JAR_FILE=build/libs/ft-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","-XX:+UseSerialGC","-Xss512k","-XX:MaxRAM=256m","/app.jar"]