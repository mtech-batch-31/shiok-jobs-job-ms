INSERT INTO sjmsjob.job (id,company_id, company_name, job_title, job_summary, job_category, level, employment_type, location, work_hours, min_salary, max_salary, posted_date, closing_date, version, last_updated_by, last_updated_time, created_by, created_time)
	VALUES
	(1, 1, 'NUS','Lecturer', 'The National University of Singapore (NUS) is seeking a dynamic and dedicated individual to join our esteemed academic community as a Lecturer. As a Lecturer at NUS, you will play a pivotal role in shaping the future of education and fostering intellectual growth within our diverse and vibrant student body.',
	'Education', 'Mid-Level', 'Full-Time', 'New York', '40 hours per week', 75000.00, 100000.00, '2023-09-23', '2023-10-23', 1, 'Admin', '2023-09-23 12:00:00', 'Admin', '2023-09-23 12:00:00'),
	(2, 2, 'Apple','Software Engineer', 'Apple Inc. is seeking talented and passionate Software Engineers to join our world-class engineering teams. As a Software Engineer at Apple, you will have the opportunity to work on groundbreaking technologies and products that impact millions of people around the world. We are looking for individuals who are innovative, collaborative, and dedicated to pushing the boundaries of what''s possible.',
	'Technology', 'Mid-Level', 'Full-Time', 'New York', '40 hours per week', 75000.00, 100000.00, '2023-09-23', '2023-10-23', 1, 'Admin', '2023-09-23 12:00:00', 'Admin', '2023-09-23 12:00:00'),
    (3, 3, 'Google','Business Analyst', 'Google LLC is seeking highly motivated and analytical individuals to join our dynamic team as Business Analysts. As a Business Analyst at Google, you will have the unique opportunity to drive data-driven decision-making, shape product strategies, and support initiatives that impact users and businesses worldwide. We are looking for candidates who are passionate about data, have a strong business acumen, and can transform insights into actionable recommendations.',
    'Data Analytics', 'Entry-Level', 'Part-Time', 'San Francisco', '20 hours per week', 55000.00, 75000.00, '2023-09-22', '2023-10-22', 1, 'Admin', '2023-09-22 12:00:00', 'Admin', '2023-09-22 12:00:00');

INSERT INTO sjmsjob.job_skills(id, skills) VALUES
    (1, 'Teaching'),
    (1, 'Research'),
    (2, 'Swift'),
    (2, 'C++'),
    (3, 'Communication'),
    (3, 'Project Management');

