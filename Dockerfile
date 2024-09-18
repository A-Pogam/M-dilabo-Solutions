networks:
  microservice-network:
    driver: bridge

services:
  db:
    image: mysql:8.0.37
    networks:
      - microservice-network
    restart: always
    environment:
      - MYSQL_DATABASE= db_testpatient
      - MYSQL_ROOT_PASSWORD= rootroot
      - MYSQL_USER= usermedilabo
      - MYSQL_PASSWORD= rootroot
    ports:
      - "3306"

  mongo:
    image:  mongodb/mongodb-community-server:7.0.9-ubi8
    ports:
      - "27017"
    networks:
      - microservice-network
    restart: always
     
  microservice-eureka:
    image: microservice-eureka:v1
    ports:
      - "9102:9102" 
    networks:
      - microservice-network
    restart: always
    environment:
      - EUREKA_INSTANCE_HOSTNAME=microservice-eureka
      - EUREKA_CLIENT_REGISTER_WITH_EUREKA=false
      - EUREKA_CLIENT_FETCH_REGISTRY=false
  
  microservice-config-server:
    image: microservice-config-server:v1
    depends_on:
      - microservice-eureka
    ports:
      - "9101:9101"
    networks:
      - microservice-network
    restart: always
    environment:
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE= http://microservice-eureka:9102/eureka/

  microservice-api-gateway:
    image: microservice-api-gateway:v1
    depends_on:
      - microservice-eureka
      - microservice-config-server
      - microservice-patients
      - microservice-medicalreports
      - microservice-assessment
    ports:
      - "9103:9103"
    networks:
      - microservice-network
    restart: always
    environment:
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE= http://microservice-eureka:9102/eureka/
   
  microservice-patients:
    image: microservice-patients:v1
    depends_on:
      - db
      - microservice-eureka
      - microservice-config-server
    ports:
      - "9001:9001"
    networks:
      - microservice-network
    restart: always
    environment:
      - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE= http://microservice-eureka:9102/eureka/
      - db
          
  microservice-medicalreports:
      image: microservice-medicalreports:v1
      depends_on:
        - microservice-eureka
      ports:
        - "9002:9002"
      networks:
        - microservice-network
      restart: always
      environment:
        - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE= http://microservice-eureka:9102/eureka/ 
        - CONN_STR=mongodb://localhost:27017   

  microservice-assessment:
      image: microservice-assessment:v1
      depends_on:
        - microservice-eureka
      ports:
        - "9003:9003"
      networks:
        - microservice-network
      restart: always
      environment:
        - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE= http://microservice-eureka:9102/eureka/
        
  microservice-client:
      image: microservice-client:v1
      depends_on:
        - microservice-eureka
        - microservice-api-gateway
      ports:
        - "8080:8080"
      networks:
        - microservice-network
      restart: always
      environment:
        - EUREKA_CLIENT_SERVICEURL_DEFAULTZONE= http://microservice-eureka:9102/eureka/