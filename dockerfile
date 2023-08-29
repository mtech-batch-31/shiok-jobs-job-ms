FROM openjdk:17-alpine

COPY build/libs/shiok_jobs_job_ms.jar /shiok_jobs_job_ms.jar
COPY src/main/resources/application.properties /application.properties
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/shiok_jobs_job_ms.jar"]
