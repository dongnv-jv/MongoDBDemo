FROM openjdk:17-oracle
WORKDIR /opt/app
COPY target/MongoDemo-0.0.1-SNAPSHOT.jar run.jar
ENTRYPOINT ["java","-jar","run.jar"]