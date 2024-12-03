FROM openjdk:17-alpine
VOLUME /tmp
ARG WAR_FILE=target/*.war
COPY ${WAR_FILE} app.war
ENTRYPOINT ["java","-jar","/app.war"]
