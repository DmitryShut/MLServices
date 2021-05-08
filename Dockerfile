FROM gradle:6.6.1-jre14 AS build
COPY build.gradle.kts /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN gradle bootJar

FROM openjdk:14-jdk-alpine
EXPOSE 8080
COPY --from=build /tmp/build/libs/demo-SNAPSHOT-boot.jar /data/demo-SNAPSHOT-boot.jar
WORKDIR /data/
CMD java -jar demo-SNAPSHOT-boot.jar