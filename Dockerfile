#FROM maven:3.9.8-eclipse-temurin-21-alpine AS build
#COPY . /app
#WORKDIR /app
#COPY pom.xml .
#RUN mvn dependency:go-offline
#COPY src/ ./src/
#RUN mvn clean install -DskipTests
#
#FROM openjdk:21-jdk-slim
#MAINTAINER <aliKalema98@gmail.com>
#COPY target/loan-calculator-0.0.1-SNAPSHOT.jar loan-calculator-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java","-jar","/loan-calculator-0.0.1-SNAPSHOT.jar"]


FROM maven:3.9.8-eclipse-temurin-21-alpine AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

#
# Package stage
#
FROM openjdk:21-jdk-slim
COPY --from=build /home/app/target/loan-calculator-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/demo.jar"]