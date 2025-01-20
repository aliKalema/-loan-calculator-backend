FROM openjdk:21-jdk-slim
MAINTAINER <aliKalema98@gmail.com>
COPY target/hrms-0.0.1-SNAPSHOT.jar hrms-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/loan-calculator-0.0.1-SNAPSHOT.jar"]