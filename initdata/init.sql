CREATE USER sjowner WITH PASSWORD '_p@ssw0rd';

CREATE DATABASE sjdb OWNER sjowner;

CREATE USER sjmsjob WITH PASSWORD '_p@ssw0rd';

\c sjdb;

CREATE SCHEMA AUTHORIZATION sjmsjob;

-- CREATE TABLE inflation_data (
--                                 RegionalMember TEXT,
--                                 Year INT,
--                                 Inflation DECIMAL,
--                                 Unit_of_Measurement TEXT,
--                                 Subregion TEXT,
--                                 Country_Code TEXT
-- );
--
-- COPY inflation_data
--     FROM '/docker-entrypoint-initdb.d/inflation.csv'
--     DELIMITER ','
--     CSV HEADER;