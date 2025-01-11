# Marvel API Framework

## Description:
This framework allows testing CRUD operations for a Marvel character creation app, where users need to authenticate (using JWT) before creating a character.

## Tech Stack:
- **Java 17**
- **Maven**
- **Rest Assured**
- **Postgres**
- **Allure** (for reporting)
- **Log4j2** (for logging)
- **JUnit 5**
- **Java Faker** (for data generation)

## Tasks:
- Automating API tests using Rest Assured
- Integration with Postgres database
- Report generation using Allure
- Logging with file storage using Log4j

## Running Tests:
1. Ensure all dependencies are installed:  
   `mvn clean install`
2. Run tests:  
   `mvn test`
3. To generate Allure reports:  
   `mvn allure:serve`  
   After executing this command, the report will open in your browser.
