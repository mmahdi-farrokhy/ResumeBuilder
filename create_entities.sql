-- Create user table
CREATE TABLE resume_builder.personal_information (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    marital_status ENUM('Single', 'Married') NOT NULL,
	gender VARCHAR(20) NOT NULL,
	military_service_status ENUM('None', 'Completed', 'Exempted', 'Educational_Exemption', 'Ongoing', 'Not_Served_Yet'),
	birth_date DATE NOT NULL,
	foreigner TINYINT(1) NOT NULL,
	disability_type VARCHAR(20) NOT NULL
);

-- Create summary table
CREATE TABLE resume_builder.summary (
    id INT PRIMARY KEY AUTO_INCREMENT,
    text VARCHAR(2000) NOT NULL
);

-- Create resume table
CREATE TABLE resume_builder.resume (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    summary_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (summary_id) REFERENCES summary(id)
);

-- Create contact_method table
CREATE TABLE resume_builder.contact_method (
    id INT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(30),
    value VARCHAR(150),
    resume_id INT,
    FOREIGN KEY (resume_id) REFERENCES resume(id)
);

-- Create education table
CREATE TABLE resume_builder.education (
    id INT PRIMARY KEY AUTO_INCREMENT,
    degree_level VARCHAR(20) NOT NULL,
    major VARCHAR(50) NOT NULL,
    university VARCHAR(100) NOT NULL,
    gpa DOUBLE NOT NULL,
    start_year INT NOT NULL,
    end_year INT NOT NULL,
    resume_id INT,
    FOREIGN KEY (resume_id) REFERENCES resume(id)
);

-- Create teaching_assistance table
CREATE TABLE resume_builder.teaching_assistance (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    university VARCHAR(100) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    resume_id INT,
    FOREIGN KEY (resume_id) REFERENCES resume(id)
);

-- Create location table
CREATE TABLE resume_builder.location (
    id INT PRIMARY KEY AUTO_INCREMENT,
    city VARCHAR(50) NOT NULL,
	country VARCHAR(50) NOT NULL,    
);

-- Create job_experience table
CREATE TABLE resume_builder.job_experience (
    id INT PRIMARY KEY AUTO_INCREMENT,
    job_title VARCHAR(100) NOT NULL,
    job_category VARCHAR(100) NOT NULL,
    seniority_level VARCHAR(20) NOT NULL,
    company_name VARCHAR(100) NOT NULL,
    job_description VARCHAR(1000) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    job_status VARCHAR(20) NOT NULL,
    location_id INT,
    resume_id INT,
    FOREIGN KEY (location_id) REFERENCES location(id),
    FOREIGN KEY (resume_id) REFERENCES resume(id)
);

-- Create former_colleague table
CREATE TABLE resume_builder.former_colleague (
    id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    position VARCHAR(100) NOT NULL,
    organization_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    resume_id INT,
    FOREIGN KEY (resume_id) REFERENCES resume(id)
);

-- Create research table
CREATE TABLE resume_builder.research (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    publisher VARCHAR(100) NOT NULL,
    reference_link VARCHAR(150) NOT NULL,
    date DATE NOT NULL,
    description VARCHAR(500) NOT NULL,
    resume_id INT,
    FOREIGN KEY (resume_id) REFERENCES resume (id)
);

-- Create course table
CREATE TABLE resume_builder.course (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    institute VARCHAR(100) NOT NULL,
    credential_id VARCHAR(100) NOT NULL,
    resume_id INT,
    FOREIGN KEY (resume_id) REFERENCES resume (id)
);

-- Create hard_skill table
CREATE TABLE resume_builder.hard_skill (
    id INT PRIMARY KEY AUTO_INCREMENT,
    hard_skill_type VARCHAR(100) NOT NULL,
    hard_skill_level VARCHAR(100) NOT NULL,
    resume_id INT,
    FOREIGN KEY (resume_id) REFERENCES resume (id)
);

-- Create soft_skill table
CREATE TABLE resume_builder.soft_skill (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    resume_id INT,
    FOREIGN KEY (resume_id) REFERENCES resume (id)
);

-- Create language table
CREATE TABLE resume_builder.language (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    speaking_level VARCHAR(30) NOT NULL,
    writing_level VARCHAR(30) NOT NULL,
    reading_level VARCHAR(30) NOT NULL,
    listening_level VARCHAR(30) NOT NULL,
    researching_level VARCHAR(30) NOT NULL,
    resume_id INT,
    FOREIGN KEY (resume_id) REFERENCES resume (id)
);

-- Create project table
CREATE TABLE resume_builder.project (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(2000),
    start_date DATE NOT NULL,
    end_date DATE,
    status VARCHAR(20) NOT NULL,
    reference_link VARCHAR(150),
    resume_id INT,
    FOREIGN KEY (resume_id) REFERENCES resume (id)
);

-- Create patent table
CREATE TABLE resume_builder.patent (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    registration_number VARCHAR(150) NOT NULL,
    registration_date DATE NOT NULL,
    reference_link VARCHAR(150),
    description VARCHAR(2000),
    resume_id INT,
    FOREIGN KEY (resume_id) REFERENCES resume (id)
);

-- Create presentation table
CREATE TABLE resume_builder.presentation (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    date DATE NOT NULL,
    reference_link VARCHAR(150),
    description VARCHAR(500) NOT NULL,
    resume_id INT,
    FOREIGN KEY (resume_id) REFERENCES resume (id)
);

-- Create award table
CREATE TABLE resume_builder.award (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    year INT NOT NULL,
    resume_id INT,
    FOREIGN KEY (resume_id) REFERENCES resume(id)
);

-- Create publication table
CREATE TABLE resume_builder.publication (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    author VARCHAR(100) NOT NULL,
    publisher VARCHAR(100) NOT NULL,
    date DATE NOT NULL,
    reference_link VARCHAR(150),
    description VARCHAR(2000) NOT NULL,
    resume_id INT,
    FOREIGN KEY (resume_id) REFERENCES resume (id)
);

-- Create volunteer_activity table
CREATE TABLE resume_builder.volunteer_activity (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    year INT NOT NULL,
    resume_id INT,
    FOREIGN KEY (resume_id) REFERENCES resume (id)
);

-- Create membership table
CREATE TABLE resume_builder.membership (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    date DATE NOT NULL,
    resume_id INT,
    FOREIGN KEY (resume_id) REFERENCES resume (id)
);

-- Create hobby table
CREATE TABLE resume_builder.hobby (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    resume_id INT,
    FOREIGN KEY (resume_id) REFERENCES resume (id)
);