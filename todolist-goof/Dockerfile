FROM maven:3-jdk-8-slim as build

RUN mkdir /usr/src/goof
COPY . /usr/src/goof
WORKDIR /usr/src/goof
RUN --mount=target=$HOME/.m2,type=cache mvn install

FROM tomcat:8.5.21

RUN mkdir /tmp/extracted_files
COPY --chown=tomcat:tomcat web.xml /usr/local/tomcat/conf/web.xml
COPY --from=build /usr/src/goof/todolist-web-struts/target/todolist.war /usr/local/tomcat/webapps/todolist.war
COPY --from=build /usr/local/openjdk-8/bin/native2ascii /docker-java-home/jre/bin/native2ascii
COPY --from=build /usr/local/openjdk-8/lib/tools.jar /docker-java-home/jre/lib/tools.jar