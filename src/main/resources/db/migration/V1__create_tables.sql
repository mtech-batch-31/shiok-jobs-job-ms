CREATE TABLE IF NOT EXISTS sjmsjob.job
(
    max_salary numeric(38,2),
    min_salary numeric(38,2),
    version integer,
    closing_date timestamp(6) ,
    company_id bigint NOT NULL,
    created_time timestamp(6),
    id bigint NOT NULL,
    last_updated_time timestamp(6),
    posted_date timestamp(6) ,
    company_name character varying(255) ,
    created_by character varying(255) ,
    employment_type character varying(255) ,
    job_category character varying(255) ,
    job_summary text ,
    job_title character varying(255) ,
    last_updated_by character varying(255) ,
    level character varying(255) ,
    location character varying(255) ,
    work_hours character varying(255) ,
    CONSTRAINT job_pkey PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS sjmsjob.job_skills
(
    id bigint NOT NULL,
    skills character varying(255) ,
    CONSTRAINT fkhvqxmfc7gu31j5dcmgh2es0fx FOREIGN KEY (id)
        REFERENCES sjmsjob.job (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);
