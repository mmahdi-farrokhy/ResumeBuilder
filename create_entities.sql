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
