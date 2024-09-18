FROM openjdk:21-oracle

WORKDIR /

COPY target/gateway-0.0.1-SNAPSHOT.jar gateway.jar
COPY src/main/resources/application.properties application.properties

EXPOSE 9091

CMD ["java", "-jar", "gateway.jar", "--spring.config.location=file:/application.properties"]
