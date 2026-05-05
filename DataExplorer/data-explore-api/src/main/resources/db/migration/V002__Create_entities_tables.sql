-- Create employees table
CREATE TABLE IF NOT EXISTS employees (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    bio TEXT
);

-- Create jobs table
CREATE TABLE IF NOT EXISTS jobs (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    department VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE
);

-- Create employee_addresses table
CREATE TABLE IF NOT EXISTS employee_addresses (
    id UUID PRIMARY KEY,
    employee_id UUID NOT NULL,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    country VARCHAR(255) NOT NULL,
    notes TEXT,
    type VARCHAR(50) NOT NULL,
    is_primary BOOLEAN NOT NULL
);

-- Create occupancies table
CREATE TABLE IF NOT EXISTS occupancies (
    id UUID PRIMARY KEY,
    employee_id UUID NOT NULL,
    job_id UUID NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    notes TEXT
);

-- Create indexes for better query performance
CREATE INDEX idx_employees_email ON employees(email);
CREATE INDEX idx_employee_addresses_employee_id ON employee_addresses(employee_id);
CREATE INDEX idx_occupancies_employee_id ON occupancies(employee_id);
CREATE INDEX idx_occupancies_job_id ON occupancies(job_id);
CREATE INDEX idx_jobs_department ON jobs(department);

