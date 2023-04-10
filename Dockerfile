#Maven Build
FROM maven:3.8.3-openjdk-17 AS builder
COPY pom.xml /app/
COPY src /app/src
RUN target=/root/.m2 mvn -f /app/pom.xml clean package -DskipTests

FROM openjdk:17-oracle
VOLUME /tmp
#ARG JAR_FILE=target/zapicito-1.0.0.jar
#ADD ${JAR_FILE} zapicito.jar
#ENTRYPOINT ["java","-jar","/zapicito.jar"]

COPY --from=builder /app/target/zapicito-1.0.0.jar zapicito.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "zapicito.jar"]
