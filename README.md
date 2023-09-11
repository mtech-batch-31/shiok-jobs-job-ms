# shiok-jobs-job-ms

1. Create Postgres database 

1a. Create a new user for the database
    postgres=# CREATE USER sjowner WITH PASSWORD '<YOUR_PASSWORD>';

1b. Create a new database and specify the owner
    postgres=# CREATE DATABASE sjdb OWNER sjowner;

1c. Create a new user for the microservice
    postgres=# CREATE USER sjmsjob WITH PASSWORD '<YOUR_PASSWORD>';

1d. Connect to sjdb
    postgres=# \c sjdb;

1d. Create schema on sjdb. User now has rights to db objects in schema
    postgres=# CREATE SCHEMA AUTHORIZATION sjmsjob;




    


