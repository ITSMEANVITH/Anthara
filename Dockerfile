# --- Build stage ---
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B clean package -DskipTests

# --- Run stage ---
FROM tomcat:10.1-jdk17
# Remove default Tomcat apps we don't need
RUN rm -rf /usr/local/tomcat/webapps/*
# Deploy our WAR as ROOT so it's served at the domain root
COPY --from=build /app/target/Anthara.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
