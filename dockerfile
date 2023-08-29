FROM openjdk:17-alpine
COPY build/libs/shiok-jobs-job-ms.jar /shiok-jobs-job-ms.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/shiok-jobs-job-ms.jar"]
