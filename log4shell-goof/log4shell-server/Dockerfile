FROM maven:3-jdk-8-slim as build
COPY . .
RUN mvn clean compile assembly:single
RUN mkdir /app
RUN jar xvf target/*.jar

FROM openjdk:8 as ldap
COPY --from=build target/*.jar /server.jar
EXPOSE 8000
EXPOSE 9999

CMD ["java", "-jar", "/server.jar"]

