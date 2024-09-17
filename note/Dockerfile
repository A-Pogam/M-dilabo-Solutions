FROM openjdk:21-oracle

WORKDIR /

COPY target/note-0.0.1-SNAPSHOT.jar note.jar
COPY src/main/resources/application.properties application.properties

CMD java -jar note.jar --spring.config.location=file:/application.properties