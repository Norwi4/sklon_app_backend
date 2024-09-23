FROM openjdk:11 as build
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/sklon-web-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} backend.jar
ENTRYPOINT ["java","-jar","/backend.jar"]
