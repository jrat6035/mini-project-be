FROM openjdk:17-oracle
COPY target/*.jar mini-project-be.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "mini-project-be.jar"]