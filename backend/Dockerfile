FROM openjdk:17
ARG JAR_FILE=build/libs/umzip-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app/umzip-0.0.1-SNAPSHOT.jar
WORKDIR /app
ENTRYPOINT ["java","-jar","umzip-0.0.1-SNAPSHOT.jar"]
