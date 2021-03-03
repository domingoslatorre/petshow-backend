FROM adoptopenjdk/openjdk11:alpine
ADD build/libs/petshow-backend-0.0.1-SNAPSHOT.jar petshow-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar", "petshow-backend-0.0.1-SNAPSHOT.jar"]