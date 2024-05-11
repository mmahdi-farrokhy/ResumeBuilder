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
* <strong>Documentation<strong>: Swagger

## How to use the API?
To use this API you should go through the steps below:
1. Clone this repository on your local system using the command below:

``` git clone https://github.com/mmahdi-farrokhy/ResumeBuilder.git```

3. Open [Postman](https://dl.pstmn.io/download/latest/win64) to send requests to the API.
4. Import the prepared requests from this [path](https://github.com/mmahdi-farrokhy/ResumeBuilder/blob/main/Postman%20Request%20Collection.json) in Postman, fill the request body with your customized data, and send the requests.
  * Inportant notes
    * First create a user, them use its data in 'Create New Resume' request body.
    * Set new resume's id for all child entities manually(resume_id column), after creating it in the database.
