CREATE TABLE IF NOT EXISTS job
(
    id bigint NOT NULL,
    closing_date timestamp(6) without time zone,
    company_id bigint NOT NULL,
    company_name character varying(255) COLLATE pg_catalog."default",
    created_by character varying(255) COLLATE pg_catalog."default",
    created_time timestamp(6) without time zone,
    employment_type character varying(255) COLLATE pg_catalog."default",
    job_category character varying(255) COLLATE pg_catalog."default",
    job_summary text COLLATE pg_catalog."default",
    job_title character varying(255) COLLATE pg_catalog."default",
    last_updated_by character varying(255) COLLATE pg_catalog."default",
    last_updated_time timestamp(6) without time zone,
    level character varying(255) COLLATE pg_catalog."default",
    location character varying(255) COLLATE pg_catalog."default",
    max_salary numeric(38,2),
    min_salary numeric(38,2),
    posted_date timestamp(6) without time zone,
    skills character varying(255) COLLATE pg_catalog."default",
    version integer,
    work_hours character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT job_pkey PRIMARY KEY (id)
)

INSERT INTO job(
	id, closing_date, company_id, company_name, created_by, created_time, employment_type, job_category, job_summary,
	job_title, last_updated_by, last_updated_time, location, max_salary, min_salary, posted_date, skills,
	version, work_hours)
	VALUES (1, '2023-01-01', 1, 'nus', 'shiokjobs', now(), null, null, null, null, null, null, null, null, null, null, null, null, null);