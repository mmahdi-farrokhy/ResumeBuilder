-- Create language table
CREATE TABLE IF NOT EXISTS resume_builder.language (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) NOT NULL,
    speaking_level VARCHAR(30) NOT NULL,
    writing_level VARCHAR(30) NOT NULL,
    reading_level VARCHAR(30) NOT NULL,
    listening_level VARCHAR(30) NOT NULL,
    researching_level VARCHAR(30) NOT NULL
);

-- Create skill table
CREATE TABLE resume_builder.skill (
    id INT PRIMARY KEY AUTO_INCREMENT,
    skill_type VARCHAR(100) NOT NULL,
    skill_level VARCHAR(100) NOT NULL
);

-- Create former_colleague table
CREATE TABLE resume_builder.former_colleague (
    id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    position VARCHAR(100) NOT NULL,
    organization_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20) NOT NULL
);

-- Create course table
CREATE TABLE resume_builder.course (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    institute VARCHAR(100) NOT NULL,
    credential_id VARCHAR(100) NOT NULL UNIQUE
);

-- Create soft_skill table
CREATE TABLE resume_builder.soft_skill (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL
);

-- Create honor table
CREATE TABLE resume_builder.honor (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    year INT NOT NULL
);

-- Create project table
CREATE TABLE resume_builder.project (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(2000),
    start_date DATE NOT NULL,
    end_date DATE,
    status VARCHAR(20) NOT NULL,
    reference_link VARCHAR(150)
);

-- Create publication table
CREATE TABLE resume_builder.publication (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    author VARCHAR(100) NOT NULL,
    publisher VARCHAR(100) NOT NULL,
    publication_date DATE NOT NULL,
    publication_url VARCHAR(150),
    description VARCHAR(2000) NOT NULL
);

-- Create volunteering table
CREATE TABLE resume_builder.volunteering (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    year INT NOT NULL
);

-- Create patent table
CREATE TABLE resume_builder.patent (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    registration_number VARCHAR(150) NOT NULL,
    registration_date DATE NOT NULL,
    reference_link VARCHAR(150),
    description VARCHAR(2000)
);

-- Create contact_information table
CREATE TABLE resume_builder.contact_information (
    id INT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(20) NOT NULL,
    value VARCHAR(150) NOT NULL
);