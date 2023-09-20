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

# deploy minikube

1. Download minikube and install
  
2. #minkube start

3. #kubectl apply -f kubernetes/deployment.yml

To delete deployment, e.g. to change the deployment yaml file, first delete with the original deployment.yml

4. #kubectl delete -f kubernetes/deployment.yml

5. Then, make changes to deployment.yaml and apply again

To check the minikube deployment,

6. #kubectl get svc

7. #kubectl get pods


