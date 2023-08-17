FROM openjdk:17-jdk-slim as buildstage
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN ./mvnw clean
RUN ./mvnw package
COPY target/*.jar app.jar

FROM openjdk:17-jdk-slim
COPY --from=buildstage /app/app.jar .
ENV JAVA_HOME /usr/local/openjdk-17
ENTRYPOINT ["java", "-jar", "app.jar"]
