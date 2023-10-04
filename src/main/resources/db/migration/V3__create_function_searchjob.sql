-- FUNCTION: sjmsjob.search_job(character varying, character varying, character varying, numeric, character varying)

-- DROP FUNCTION IF EXISTS sjmsjob.search_job(character varying, character varying, character varying, numeric, character varying);

CREATE OR REPLACE FUNCTION sjmsjob.search_job(
	keywords_input character varying,
	employmenttypes_input character varying,
	worklocations_input character varying,
	minialary_input numeric,
	skills_input character varying)
    RETURNS SETOF job 
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
#variable_conflict use_column
begin 
	if keywords_input IS NOT NULL AND keywords_input != '' then
		RETURN QUERY
SELECT DISTINCT j.*
FROM sjmsjob.job j
   , to_tsvector(j.job_title || ' ' || j.job_summary) as searched
   , to_tsquery('english', keywords_input) query
   , ts_rank(to_tsvector(j.job_title || ' ' || j.job_summary || ' ' || j.company_name), query) as search_rank
   , job_skills s
WHERE j.id = s.id AND search_rank > 0
  AND (employmentTypes_input IS NULL OR  employmentTypes_input = '' OR employment_type = ANY(string_to_array(employmentTypes_input, '|')))
  AND (worklocations_input IS NULL OR  worklocations_input = '' OR "location" = ANY(string_to_array(worklocations_input, '|')))
  AND (min_Salary >= minialary_input OR max_Salary >= minialary_input)
  AND (skills_input IS NULL OR  skills_input = '' OR LOWER(s.skills) = ANY(string_to_array(LOWER(skills_input), '|')))
;--ORDER BY search_rank DESC;
else
		RETURN QUERY
SELECT DISTINCT j.*
FROM sjmsjob.job j
   , job_skills s
WHERE j.id = s.id
  AND (employmentTypes_input IS NULL OR  employmentTypes_input = '' OR employment_type = ANY(string_to_array(employmentTypes_input, '|')))
  AND (worklocations_input IS NULL OR  worklocations_input = '' OR "location" = ANY(string_to_array(worklocations_input, '|')))
  AND (min_Salary >= minialary_input OR max_Salary >= minialary_input)
  AND (skills_input IS NULL OR  skills_input = '' OR LOWER(s.skills) = ANY(string_to_array(LOWER(skills_input), '|')))
;
end if;
	return;
end; 
$BODY$;


