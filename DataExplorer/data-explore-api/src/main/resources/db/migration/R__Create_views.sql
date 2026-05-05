CREATE OR REPLACE VIEW person_view AS
SELECT
    employee.id,
    employee.first_name,
    employee.last_name,
    employee.email,
    employee.phone_number,
    employee.bio,
    job.id AS job_id,
    job.title AS job_title,
    occupancy.start_date,
    occupancy.end_date
FROM employees employee
INNER JOIN occupancies occupancy ON employee.id = occupancy.employee_id
INNER JOIN jobs job ON occupancy.job_id = job.id;


CREATE OR REPLACE VIEW address_view AS
SELECT
    address.id,
    address.city,
    address.street
FROM employee_addresses address;
