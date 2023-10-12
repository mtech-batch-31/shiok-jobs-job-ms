CREATE USER sjowner WITH PASSWORD '_p@ssw0rd';

CREATE DATABASE sjdb OWNER sjowner;

CREATE USER sjmsjob WITH PASSWORD '_p@ssw0rd';

\c sjdb;

CREATE SCHEMA AUTHORIZATION sjmsjob;

