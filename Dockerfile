FROM graalvm/jdk:21.0.0.2-ce
ARG JAR_FILE=target/*.jar
WORKDIR /opt
ENV PORT 9091
EXPOSE 9091
COPY ${JAR_FILE} ecnic-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "ecnic-service-0.0.1-SNAPSHOT.jar"]