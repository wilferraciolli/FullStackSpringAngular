-- ── Employees (600 rows, unique emails via sequence number) ──────────────────
INSERT INTO employees (id, email, first_name, last_name, phone_number, bio)
SELECT
    RANDOM_UUID(),
    'user' || X || '@company.com',
    CASE MOD(X, 20)
        WHEN 0  THEN 'James'    WHEN 1  THEN 'Mary'      WHEN 2  THEN 'Robert'   WHEN 3  THEN 'Patricia'
        WHEN 4  THEN 'John'     WHEN 5  THEN 'Jennifer'  WHEN 6  THEN 'Michael'  WHEN 7  THEN 'Linda'
        WHEN 8  THEN 'David'    WHEN 9  THEN 'Barbara'   WHEN 10 THEN 'William'  WHEN 11 THEN 'Elizabeth'
        WHEN 12 THEN 'Richard'  WHEN 13 THEN 'Susan'     WHEN 14 THEN 'Joseph'   WHEN 15 THEN 'Jessica'
        WHEN 16 THEN 'Thomas'   WHEN 17 THEN 'Sarah'     WHEN 18 THEN 'Charles'  ELSE        'Karen'
    END,
    CASE MOD(X, 20)
        WHEN 0  THEN 'Smith'    WHEN 1  THEN 'Johnson'   WHEN 2  THEN 'Williams' WHEN 3  THEN 'Brown'
        WHEN 4  THEN 'Jones'    WHEN 5  THEN 'Garcia'    WHEN 6  THEN 'Miller'   WHEN 7  THEN 'Davis'
        WHEN 8  THEN 'Wilson'   WHEN 9  THEN 'Anderson'  WHEN 10 THEN 'Taylor'   WHEN 11 THEN 'Thomas'
        WHEN 12 THEN 'Jackson'  WHEN 13 THEN 'White'     WHEN 14 THEN 'Harris'   WHEN 15 THEN 'Martin'
        WHEN 16 THEN 'Thompson' WHEN 17 THEN 'Moore'     WHEN 18 THEN 'Allen'    ELSE        'Clark'
    END,
    '555-' || LPAD(CAST(X AS VARCHAR), 4, '0'),
    CASE MOD(X, 10)
        WHEN 0 THEN 'Senior Software Engineer with ' || (5 + MOD(X, 15)) || ' years experience'
        WHEN 1 THEN 'Product Manager focused on innovation and delivery'
        WHEN 2 THEN 'Data Analyst specialising in business intelligence'
        WHEN 3 THEN 'UX Designer passionate about user-centred design'
        WHEN 4 THEN 'DevOps Engineer automating cloud infrastructure'
        WHEN 5 THEN 'Marketing Specialist driving brand awareness'
        WHEN 6 THEN 'HR Business Partner supporting talent growth'
        WHEN 7 THEN 'Finance Analyst tracking budgets and forecasts'
        WHEN 8 THEN 'QA Engineer ensuring software quality standards'
        ELSE        'Business Analyst bridging tech and business'
    END
FROM generate_series(1, 600);

-- ── Jobs (40 roles across departments) ───────────────────────────────────────
INSERT INTO jobs (id, title, department, description, start_date, end_date) VALUES
(RANDOM_UUID(), 'Senior Software Engineer',    'Engineering',        'Lead development of core platform features',  '2026-01-01', NULL),
(RANDOM_UUID(), 'Product Manager',             'Product',            'Manage product roadmap and strategy',         '2026-01-15', NULL),
(RANDOM_UUID(), 'Data Analyst',                'Analytics',          'Analyse business data and generate insights', '2026-02-01', NULL),
(RANDOM_UUID(), 'UX Designer',                 'Design',             'Design user interfaces and experiences',      '2026-02-10', NULL),
(RANDOM_UUID(), 'DevOps Engineer',             'Infrastructure',     'Manage cloud infrastructure and deployments', '2026-03-01', NULL),
(RANDOM_UUID(), 'Marketing Manager',           'Marketing',          'Lead marketing campaigns and initiatives',    '2026-03-15', NULL),
(RANDOM_UUID(), 'HR Manager',                  'Human Resources',    'Manage recruitment and employee relations',   '2026-04-01', NULL),
(RANDOM_UUID(), 'Finance Director',            'Finance',            'Oversee financial operations',                '2026-04-15', NULL),
(RANDOM_UUID(), 'QA Lead',                     'Quality Assurance',  'Lead quality assurance efforts',              '2026-05-01', NULL),
(RANDOM_UUID(), 'Business Analyst',            'Business',           'Analyse business requirements',               '2026-05-15', NULL),
(RANDOM_UUID(), 'Frontend Developer',          'Engineering',        'Develop frontend applications',               '2026-06-01', NULL),
(RANDOM_UUID(), 'Backend Developer',           'Engineering',        'Develop backend services',                    '2026-06-15', NULL),
(RANDOM_UUID(), 'Security Engineer',           'Security',           'Ensure system security',                      '2026-07-01', NULL),
(RANDOM_UUID(), 'Project Manager',             'Project Management', 'Manage projects and timelines',               '2026-07-15', NULL),
(RANDOM_UUID(), 'Systems Administrator',       'IT Operations',      'Manage IT systems',                           '2026-08-01', NULL),
(RANDOM_UUID(), 'Content Writer',              'Marketing',          'Create content for marketing',                '2026-08-15', NULL),
(RANDOM_UUID(), 'Sales Manager',               'Sales',              'Manage sales team performance',               '2026-09-01', NULL),
(RANDOM_UUID(), 'Recruiter',                   'Human Resources',    'Recruit and hire talent',                     '2026-09-15', NULL),
(RANDOM_UUID(), 'Solution Architect',          'Engineering',        'Design technical solutions',                  '2026-10-01', NULL),
(RANDOM_UUID(), 'ML Engineer',                 'Data Science',       'Build machine learning models',               '2026-10-15', NULL),
(RANDOM_UUID(), 'Tech Lead',                   'Engineering',        'Lead technical team',                         '2026-11-01', NULL),
(RANDOM_UUID(), 'CTO',                         'Executive',          'Chief Technology Officer',                    '2026-11-15', NULL),
(RANDOM_UUID(), 'Operations Manager',          'Operations',         'Manage operational processes',                '2026-12-01', NULL),
(RANDOM_UUID(), 'Training Coordinator',        'Human Resources',    'Coordinate training programs',                '2026-01-01', NULL),
(RANDOM_UUID(), 'Network Administrator',       'IT Operations',      'Manage network infrastructure',               '2026-01-15', NULL),
(RANDOM_UUID(), 'Data Scientist',              'Data Science',       'Perform advanced data analysis',              '2026-02-01', NULL),
(RANDOM_UUID(), 'API Developer',               'Engineering',        'Develop and maintain APIs',                   '2026-02-15', NULL),
(RANDOM_UUID(), 'Database Administrator',      'IT Operations',      'Manage databases',                            '2026-03-01', NULL),
(RANDOM_UUID(), 'Mobile Developer',            'Engineering',        'Develop mobile applications',                 '2026-03-15', NULL),
(RANDOM_UUID(), 'Compliance Officer',          'Legal',              'Ensure regulatory compliance',                '2026-04-01', NULL),
(RANDOM_UUID(), 'Customer Success Manager',    'Customer Support',   'Manage customer success',                     '2026-04-15', NULL),
(RANDOM_UUID(), 'Internal Auditor',            'Finance',            'Conduct internal audits',                     '2026-05-01', NULL),
(RANDOM_UUID(), 'Supply Chain Manager',        'Operations',         'Manage supply chain',                         '2026-05-15', NULL),
(RANDOM_UUID(), 'IT Manager',                  'IT Operations',      'Manage IT department',                        '2026-06-01', NULL),
(RANDOM_UUID(), 'Legal Counsel',               'Legal',              'Provide legal counsel',                       '2026-06-15', NULL),
(RANDOM_UUID(), 'Communications Manager',      'Marketing',          'Manage communications',                       '2026-07-01', NULL),
(RANDOM_UUID(), 'Facilities Manager',          'Operations',         'Manage facilities',                           '2026-07-15', NULL),
(RANDOM_UUID(), 'Research Scientist',          'Research',           'Conduct research',                            '2026-08-01', NULL),
(RANDOM_UUID(), 'CFO',                         'Executive',          'Chief Financial Officer',                     '2026-08-15', NULL),
(RANDOM_UUID(), 'Procurement Manager',         'Operations',         'Manage procurement',                          '2026-09-01', NULL);

-- ── Addresses — one residential per employee (600 rows) ──────────────────────
INSERT INTO employee_addresses (id, employee_id, street, city, state, zip_code, country, notes, type, is_primary)
SELECT
    RANDOM_UUID(),
    e.id,
    CASE MOD(ROW_NUMBER() OVER (), 8)
        WHEN 0 THEN '10 Maple Avenue'    WHEN 1 THEN '24 Oak Street'      WHEN 2 THEN '7 Elm Road'
        WHEN 3 THEN '88 Cedar Lane'      WHEN 4 THEN '3 Pine Close'       WHEN 5 THEN '15 Birch Way'
        WHEN 6 THEN '42 Willow Drive'    ELSE        '9 Ash Court'
    END,
    CASE MOD(ROW_NUMBER() OVER (), 10)
        WHEN 0 THEN 'New York'   WHEN 1 THEN 'Chicago'    WHEN 2 THEN 'Los Angeles' WHEN 3 THEN 'Houston'
        WHEN 4 THEN 'Phoenix'    WHEN 5 THEN 'Austin'     WHEN 6 THEN 'Seattle'     WHEN 7 THEN 'Boston'
        WHEN 8 THEN 'Denver'     ELSE        'Miami'
    END,
    'NY', '10001', 'USA', 'Primary home address', 'RESIDENTIAL', true
FROM employees e;

-- ── Addresses — one business address for the first 400 employees ─────────────
INSERT INTO employee_addresses (id, employee_id, street, city, state, zip_code, country, notes, type, is_primary)
SELECT
    RANDOM_UUID(),
    e.id,
    '200 Corporate Blvd',
    CASE MOD(ROW_NUMBER() OVER (), 5)
        WHEN 0 THEN 'New York' WHEN 1 THEN 'Chicago' WHEN 2 THEN 'Los Angeles'
        WHEN 3 THEN 'Houston'  ELSE        'Seattle'
    END,
    'NY', '10002', 'USA', 'Office location', 'BUSINESS', false
FROM (SELECT id FROM employees LIMIT 400) e;

-- ── Occupancies — current positions (1 000 rows, dates in past-year window) ──
INSERT INTO occupancies (id, employee_id, job_id, start_date, end_date, notes)
SELECT
    RANDOM_UUID(),
    (ARRAY(SELECT id FROM employees ORDER BY RANDOM() LIMIT 1))[1],
    (ARRAY(SELECT id FROM jobs    ORDER BY RANDOM() LIMIT 1))[1],
    '2025-05-01'::DATE + (RANDOM() * 365)::INT,
    NULL,
    'Currently occupying this position'
FROM generate_series(1, 1000);

-- ── Occupancies — completed/historical positions (500 rows, 2023-2024) ───────
INSERT INTO occupancies (id, employee_id, job_id, start_date, end_date, notes)
SELECT
    RANDOM_UUID(),
    (ARRAY(SELECT id FROM employees ORDER BY RANDOM() LIMIT 1))[1],
    (ARRAY(SELECT id FROM jobs    ORDER BY RANDOM() LIMIT 1))[1],
    '2023-01-01'::DATE + (RANDOM() * 365)::INT,
    '2024-01-01'::DATE + (RANDOM() * 365)::INT,
    'Previously occupied this position'
FROM generate_series(1, 500);

-- ── Occupancies — on leave / sabbatical (200 rows, 2025-2026) ────────────────
INSERT INTO occupancies (id, employee_id, job_id, start_date, end_date, notes)
SELECT
    RANDOM_UUID(),
    (ARRAY(SELECT id FROM employees ORDER BY RANDOM() LIMIT 1))[1],
    (ARRAY(SELECT id FROM jobs    ORDER BY RANDOM() LIMIT 1))[1],
    '2025-06-01'::DATE + (RANDOM() * 300)::INT,
    NULL,
    'Employee on sabbatical'
FROM generate_series(1, 200);
