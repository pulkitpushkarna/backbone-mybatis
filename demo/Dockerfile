FROM openjdk:8
ADD build/libs/*.jar dockerapp.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "dockerapp.jar"]