FROM openjdk:17-oracle
ADD target/zapicito-0.0.1-SNAPSHOT.jar zapicito-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "zapicito-0.0.1-SNAPSHOT.jar"]