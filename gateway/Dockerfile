FROM openjdk:21-oracle

WORKDIR /

COPY target/gateway-0.0.1-SNAPSHOT.jar gateway.jar
COPY src/main/ressources/application.properties application.properties

EXPOSE 3000

CMD java -jar gateway.jar --spring.config.location=file:/application.properties