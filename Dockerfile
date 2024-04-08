FROM openjdk:18
EXPOSE 8080
ADD build/libs/user-service-0.1.jar user-service-0.1.jar
ENTRYPOINT ["java", "-jar", "/user-service-0.1.jar"]
