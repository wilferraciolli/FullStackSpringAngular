-- Insert sample data for employees
INSERT INTO employees (id, email, first_name, last_name, phone_number, bio) VALUES
(RANDOM_UUID(), 'john.smith@example.com', 'John', 'Smith', '555-0001', 'Senior Software Engineer with 10 years experience'),
(RANDOM_UUID(), 'sarah.johnson@example.com', 'Sarah', 'Johnson', '555-0002', 'Product Manager focused on innovation'),
(RANDOM_UUID(), 'michael.brown@example.com', 'Michael', 'Brown', '555-0003', 'Data Analyst specializing in business intelligence'),
(RANDOM_UUID(), 'emily.davis@example.com', 'Emily', 'Davis', '555-0004', 'UX Designer with passion for user experience'),
(RANDOM_UUID(), 'david.wilson@example.com', 'David', 'Wilson', '555-0005', 'DevOps Engineer automating infrastructure'),
(RANDOM_UUID(), 'jessica.miller@example.com', 'Jessica', 'Miller', '555-0006', 'Marketing Specialist'),
(RANDOM_UUID(), 'robert.moore@example.com', 'Robert', 'Moore', '555-0007', 'HR Manager'),
(RANDOM_UUID(), 'lisa.taylor@example.com', 'Lisa', 'Taylor', '555-0008', 'Finance Director'),
(RANDOM_UUID(), 'james.anderson@example.com', 'James', 'Anderson', '555-0009', 'Quality Assurance Lead'),
(RANDOM_UUID(), 'patricia.thomas@example.com', 'Patricia', 'Thomas', '555-0010', 'Business Analyst'),
(RANDOM_UUID(), 'charles.jackson@example.com', 'Charles', 'Jackson', '555-0011', 'Frontend Developer'),
(RANDOM_UUID(), 'angela.white@example.com', 'Angela', 'White', '555-0012', 'Backend Developer'),
(RANDOM_UUID(), 'mark.harris@example.com', 'Mark', 'Harris', '555-0013', 'Security Engineer'),
(RANDOM_UUID(), 'karen.martin@example.com', 'Karen', 'Martin', '555-0014', 'Project Manager'),
(RANDOM_UUID(), 'paul.thompson@example.com', 'Paul', 'Thompson', '555-0015', 'Systems Administrator'),
(RANDOM_UUID(), 'nancy.garcia@example.com', 'Nancy', 'Garcia', '555-0016', 'Content Writer'),
(RANDOM_UUID(), 'steven.martinez@example.com', 'Steven', 'Martinez', '555-0017', 'Sales Manager'),
(RANDOM_UUID(), 'donna.robinson@example.com', 'Donna', 'Robinson', '555-0018', 'Recruiter'),
(RANDOM_UUID(), 'kevin.clark@example.com', 'Kevin', 'Clark', '555-0019', 'Solution Architect'),
(RANDOM_UUID(), 'sandra.rodriguez@example.com', 'Sandra', 'Rodriguez', '555-0020', 'ML Engineer'),
(RANDOM_UUID(), 'brian.lewis@example.com', 'Brian', 'Lewis', '555-0021', 'Tech Lead'),
(RANDOM_UUID(), 'cynthia.lee@example.com', 'Cynthia', 'Lee', '555-0022', 'Chief Technology Officer'),
(RANDOM_UUID(), 'edward.walker@example.com', 'Edward', 'Walker', '555-0023', 'Operations Manager'),
(RANDOM_UUID(), 'betty.king@example.com', 'Betty', 'King', '555-0024', 'Training Coordinator'),
(RANDOM_UUID(), 'ronald.wright@example.com', 'Ronald', 'Wright', '555-0025', 'Network Administrator'),
(RANDOM_UUID(), 'sandra.lopez@example.com', 'Sandra', 'Lopez', '555-0026', 'Data Scientist'),
(RANDOM_UUID(), 'timothy.hill@example.com', 'Timothy', 'Hill', '555-0027', 'API Developer'),
(RANDOM_UUID(), 'carol.scott@example.com', 'Carol', 'Scott', '555-0028', 'Database Administrator'),
(RANDOM_UUID(), 'jason.green@example.com', 'Jason', 'Green', '555-0029', 'Mobile Developer'),
(RANDOM_UUID(), 'brenda.adams@example.com', 'Brenda', 'Adams', '555-0030', 'Compliance Officer'),
(RANDOM_UUID(), 'gary.nelson@example.com', 'Gary', 'Nelson', '555-0031', 'Customer Success Manager'),
(RANDOM_UUID(), 'pamela.carter@example.com', 'Pamela', 'Carter', '555-0032', 'Internal Audit'),
(RANDOM_UUID(), 'ryan.mitchell@example.com', 'Ryan', 'Mitchell', '555-0033', 'Supply Chain Manager'),
(RANDOM_UUID(), 'deborah.roberts@example.com', 'Deborah', 'Roberts', '555-0034', 'IT Manager'),
(RANDOM_UUID(), 'jacob.phillips@example.com', 'Jacob', 'Phillips', '555-0035', 'Legal Counsel'),
(RANDOM_UUID(), 'susan.campbell@example.com', 'Susan', 'Campbell', '555-0036', 'Communications Manager'),
(RANDOM_UUID(), 'thomas.parker@example.com', 'Thomas', 'Parker', '555-0037', 'Facilities Manager'),
(RANDOM_UUID(), 'katherine.evans@example.com', 'Katherine', 'Evans', '555-0038', 'Research Scientist'),
(RANDOM_UUID(), 'matthew.edwards@example.com', 'Matthew', 'Edwards', '555-0039', 'Chief Financial Officer'),
(RANDOM_UUID(), 'catherine.collins@example.com', 'Catherine', 'Collins', '555-0040', 'Procurement Manager'),
(RANDOM_UUID(), 'anthony.stewart@example.com', 'Anthony', 'Stewart', '555-0041', 'Environmental Health Officer'),
(RANDOM_UUID(), 'joanne.sanchez@example.com', 'Joanne', 'Sanchez', '555-0042', 'Graphic Designer'),
(RANDOM_UUID(), 'mark.morris@example.com', 'Mark', 'Morris', '555-0043', 'Chief Executive Officer'),
(RANDOM_UUID(), 'diane.rogers@example.com', 'Diane', 'Rogers', '555-0044', 'Executive Assistant'),
(RANDOM_UUID(), 'donald.reed@example.com', 'Donald', 'Reed', '555-0045', 'Operations Director'),
(RANDOM_UUID(), 'christine.cook@example.com', 'Christine', 'Cook', '555-0046', 'General Counsel'),
(RANDOM_UUID(), 'paul.morgan@example.com', 'Paul', 'Morgan', '555-0047', 'Chief Operating Officer'),
(RANDOM_UUID(), 'janet.peterson@example.com', 'Janet', 'Peterson', '555-0048', 'Board Member'),
(RANDOM_UUID(), 'kenneth.gray@example.com', 'Kenneth', 'Gray', '555-0049', 'Advisor'),
(RANDOM_UUID(), 'joyce.ramirez@example.com', 'Joyce', 'Ramirez', '555-0050', 'Senior Consultant'),
(RANDOM_UUID(), 'richard.james@example.com', 'Richard', 'James', '555-0051', 'Strategy Manager'),
(RANDOM_UUID(), 'kathleen.watson@example.com', 'Kathleen', 'Watson', '555-0052', 'Brand Manager'),
(RANDOM_UUID(), 'joseph.brooks@example.com', 'Joseph', 'Brooks', '555-0053', 'Sales Engineer'),
(RANDOM_UUID(), 'shirley.kelly@example.com', 'Shirley', 'Kelly', '555-0054', 'Contract Manager'),
(RANDOM_UUID(), 'william.sanders@example.com', 'William', 'Sanders', '555-0055', 'Vendor Manager'),
(RANDOM_UUID(), 'anna.price@example.com', 'Anna', 'Price', '555-0056', 'Risk Manager'),
(RANDOM_UUID(), 'andrew.bennett@example.com', 'Andrew', 'Bennett', '555-0057', 'Portfolio Manager'),
(RANDOM_UUID(), 'barbara.wood@example.com', 'Barbara', 'Wood', '555-0058', 'Investment Manager'),
(RANDOM_UUID(), 'kenneth.barnes@example.com', 'Kenneth', 'Barnes', '555-0059', 'Portfolio Analyst'),
(RANDOM_UUID(), 'alice.ross@example.com', 'Alice', 'Ross', '555-0060', 'Financial Advisor');

-- Insert sample data for jobs
INSERT INTO jobs (id, title, department, description, start_date, end_date) VALUES
(RANDOM_UUID(), 'Senior Software Engineer', 'Engineering', 'Lead development of core platform features', '2024-01-01', NULL),
(RANDOM_UUID(), 'Product Manager', 'Product', 'Manage product roadmap and strategy', '2024-01-15', NULL),
(RANDOM_UUID(), 'Data Analyst', 'Analytics', 'Analyze business data and generate insights', '2024-02-01', NULL),
(RANDOM_UUID(), 'UX Designer', 'Design', 'Design user interfaces and experiences', '2024-02-10', NULL),
(RANDOM_UUID(), 'DevOps Engineer', 'Infrastructure', 'Manage cloud infrastructure and deployment', '2024-03-01', NULL),
(RANDOM_UUID(), 'Marketing Manager', 'Marketing', 'Lead marketing campaigns and initiatives', '2024-03-15', NULL),
(RANDOM_UUID(), 'HR Manager', 'Human Resources', 'Manage recruitment and employee relations', '2024-04-01', NULL),
(RANDOM_UUID(), 'Finance Director', 'Finance', 'Oversee financial operations', '2024-04-15', NULL),
(RANDOM_UUID(), 'QA Lead', 'Quality Assurance', 'Lead quality assurance efforts', '2024-05-01', NULL),
(RANDOM_UUID(), 'Business Analyst', 'Business', 'Analyze business requirements', '2024-05-15', NULL),
(RANDOM_UUID(), 'Frontend Developer', 'Engineering', 'Develop frontend applications', '2024-06-01', NULL),
(RANDOM_UUID(), 'Backend Developer', 'Engineering', 'Develop backend services', '2024-06-15', NULL),
(RANDOM_UUID(), 'Security Engineer', 'Security', 'Ensure system security', '2024-07-01', NULL),
(RANDOM_UUID(), 'Project Manager', 'Project Management', 'Manage projects and timelines', '2024-07-15', NULL),
(RANDOM_UUID(), 'Systems Administrator', 'IT Operations', 'Manage IT systems', '2024-08-01', NULL),
(RANDOM_UUID(), 'Content Writer', 'Marketing', 'Create content for marketing', '2024-08-15', NULL),
(RANDOM_UUID(), 'Sales Manager', 'Sales', 'Manage sales team performance', '2024-09-01', NULL),
(RANDOM_UUID(), 'Recruiter', 'Human Resources', 'Recruit and hire talent', '2024-09-15', NULL),
(RANDOM_UUID(), 'Solution Architect', 'Engineering', 'Design technical solutions', '2024-10-01', NULL),
(RANDOM_UUID(), 'ML Engineer', 'Data Science', 'Build machine learning models', '2024-10-15', NULL),
(RANDOM_UUID(), 'Tech Lead', 'Engineering', 'Lead technical team', '2024-11-01', NULL),
(RANDOM_UUID(), 'CTO', 'Executive', 'Chief Technology Officer', '2024-11-15', NULL),
(RANDOM_UUID(), 'Operations Manager', 'Operations', 'Manage operational processes', '2024-12-01', NULL),
(RANDOM_UUID(), 'Training Coordinator', 'Human Resources', 'Coordinate training programs', '2025-01-01', NULL),
(RANDOM_UUID(), 'Network Administrator', 'IT Operations', 'Manage network infrastructure', '2025-01-15', NULL),
(RANDOM_UUID(), 'Data Scientist', 'Data Science', 'Perform advanced data analysis', '2025-02-01', NULL),
(RANDOM_UUID(), 'API Developer', 'Engineering', 'Develop and maintain APIs', '2025-02-15', NULL),
(RANDOM_UUID(), 'Database Administrator', 'IT Operations', 'Manage databases', '2025-03-01', NULL),
(RANDOM_UUID(), 'Mobile Developer', 'Engineering', 'Develop mobile applications', '2025-03-15', NULL),
(RANDOM_UUID(), 'Compliance Officer', 'Legal', 'Ensure regulatory compliance', '2025-04-01', NULL),
(RANDOM_UUID(), 'Customer Success Manager', 'Customer Support', 'Manage customer success', '2025-04-15', NULL),
(RANDOM_UUID(), 'Internal Audit', 'Finance', 'Conduct internal audits', '2025-05-01', NULL),
(RANDOM_UUID(), 'Supply Chain Manager', 'Operations', 'Manage supply chain', '2025-05-15', NULL),
(RANDOM_UUID(), 'IT Manager', 'IT Operations', 'Manage IT department', '2025-06-01', NULL),
(RANDOM_UUID(), 'Legal Counsel', 'Legal', 'Provide legal counsel', '2025-06-15', NULL),
(RANDOM_UUID(), 'Communications Manager', 'Marketing', 'Manage communications', '2025-07-01', NULL),
(RANDOM_UUID(), 'Facilities Manager', 'Operations', 'Manage facilities', '2025-07-15', NULL),
(RANDOM_UUID(), 'Research Scientist', 'Research', 'Conduct research', '2025-08-01', NULL),
(RANDOM_UUID(), 'CFO', 'Executive', 'Chief Financial Officer', '2025-08-15', NULL),
(RANDOM_UUID(), 'Procurement Manager', 'Operations', 'Manage procurement', '2025-09-01', NULL),
(RANDOM_UUID(), 'Environmental Health Officer', 'Compliance', 'Ensure environmental health', '2025-09-15', NULL);

-- Insert sample addresses for employees (multiple addresses per employee)
INSERT INTO employee_addresses (id, employee_id, street, city, state, zip_code, country, notes, type, is_primary)
SELECT RANDOM_UUID(), e.id, '123 Main St', 'New York', 'NY', '10001', 'USA', 'Home address', 'RESIDENTIAL', true
FROM (SELECT id FROM employees LIMIT 60) e;

-- Insert additional addresses for some employees
INSERT INTO employee_addresses (id, employee_id, street, city, state, zip_code, country, notes, type, is_primary)
SELECT RANDOM_UUID(), e.id, '456 Business Ave', 'New York', 'NY', '10002', 'USA', 'Office location', 'BUSINESS', false
FROM (SELECT id FROM employees LIMIT 40 OFFSET 0) e;

-- Insert sample occupancy records (employees occupying jobs over time periods)
INSERT INTO occupancies (id, employee_id, job_id, start_date, end_date, notes)
SELECT
    RANDOM_UUID(),
    (ARRAY(SELECT id FROM employees ORDER BY RANDOM() LIMIT 1))[1],
    (ARRAY(SELECT id FROM jobs ORDER BY RANDOM() LIMIT 1))[1],
    '2024-01-01'::DATE + (RANDOM() * 365)::INT,
    NULL,
    'Currently occupying this position'
FROM generate_series(1, 100);

-- Insert additional occupancy records for completed positions
INSERT INTO occupancies (id, employee_id, job_id, start_date, end_date, notes)
SELECT
    RANDOM_UUID(),
    (ARRAY(SELECT id FROM employees ORDER BY RANDOM() LIMIT 1))[1],
    (ARRAY(SELECT id FROM jobs ORDER BY RANDOM() LIMIT 1))[1],
    '2023-01-01'::DATE + (RANDOM() * 365)::INT,
    '2024-01-01'::DATE + (RANDOM() * 365)::INT,
    'Previously occupied this position'
FROM generate_series(1, 50);

-- Insert additional occupancy records for on-leave positions
INSERT INTO occupancies (id, employee_id, job_id, start_date, end_date, notes)
SELECT
    RANDOM_UUID(),
    (ARRAY(SELECT id FROM employees ORDER BY RANDOM() LIMIT 1))[1],
    (ARRAY(SELECT id FROM jobs ORDER BY RANDOM() LIMIT 1))[1],
    '2024-06-01'::DATE + (RANDOM() * 180)::INT,
    NULL,
    'Employee on sabbatical'
FROM generate_series(1, 20);

