# Resume Generator API
## Project Overview
Welcome to the resume generator API!
This project provides a Spring Boot API for generating resume files as Word documents. Users can sign up/in to create, edit or delete their resumes.
The API supports various themes for the generated documents:
  1. ATS Classic
  2. Wood Working
  3. Simple Florist
  4. Classic Accounting

## Technology Stack
* <strong>Framework<strong>: Spring Boot
* <strong>Language<strong>: Java 21
* <strong>Database<strong>: MySQL
* <strong>ORM<strong>: Hibernate
* <strong>Documentation<strong>: Swagger UI/Spring OpenAPI

## How to use the API?
To use this API you should go through the steps below:
1. Clone this repository on your local system using this command:

    ``` git clone https://github.com/mmahdi-farrokhy/ResumeBuilder.git```

2. Launch [Postman](https://dl.pstmn.io/download/latest/win64) to send requests to the API.
3. Import the prepared requests from this [path](https://github.com/mmahdi-farrokhy/ResumeBuilder/blob/main/Postman%20Request%20Collection.json) in Postman.
4. Fill the request body with your customized data.
5. Send the requests.
    * Important notes
      * First create a user, then use its data in 'Create New Resume' request body for object 'user'.
      * Set new resume's id for all child entities manually(resume_id column), after creating it in the database.
6. 'Download Resume' request fetches a resume with all its child entities from database and creates a resume file with .docx extension (MS Word) in 'resumes' folder in project's path.

## Sample Generated Resumes
[Here](https://github.com/mmahdi-farrokhy/ResumeBuilder/tree/main/Sample%20Resumes) you can see my own resumes generated using '[Resume Builder API](https://github.com/mmahdi-farrokhy/ResumeBuilder)'
