FROM openjdk:21-oracle

WORKDIR /

COPY target/service-discovery-0.0.1-SNAPSHOT.jar service-discovery.jar
COPY src/main/ressources/application.properties application.properties

CMD java -jar service-discovery.jar --spring.config.location=file:/application.properties