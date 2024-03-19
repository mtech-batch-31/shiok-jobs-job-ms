FROM openjdk:17

COPY build/libs/shiok_jobs_job_ms.jar /shiok_jobs_job_ms.jar
COPY src/main/resources/application.properties /application.properties
COPY src/main/resources/db/migration /db/migration
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/shiok_jobs_job_ms.jar"]
