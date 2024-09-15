FROM openjdk:21-oracle

WORKDIR /

COPY target/diabete-0.0.1-SNAPSHOT.jar diabete.jar
COPY src/main/ressources/application.properties application.properties

CMD java -jar diabete.jar --spring.config.location=file:/application.properties