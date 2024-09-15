FROM openjdk:21-oracle

WORKDIR /app

COPY target/patient-0.0.1-SNAPSHOT.jar patient.jar
COPY src/main/ressources/application.properties application.properties

CMD java -jar patient.jar --spring.config.location=file:/application.properties