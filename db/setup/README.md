# Database Setup

This directory contains scripts and resources for setting up the database for the project.

## Prerequisites

- **Database**: Ensure you have a compatible SQL database installed (e.g., MySQL, PostgreSQL).
- **Java**: Java 17 or higher is required.
- **Maven**: Ensure Maven is installed for dependency management.

## Setup Instructions

1. **Create the Database**:
    - Run the SQL scripts in the `scripts` folder to create the database schema and populate initial data.

2. **Configure Application**:
    - Update the `application.properties` file in the Spring Boot project with the correct database connection details:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/your_database
      spring.datasource.username=your_username
      spring.datasource.password=your_password
      ```

3. **Run the Application**:
    - Start the Spring Boot application to verify the database connection.

## Notes

- Ensure the database user has the necessary permissions to create tables and insert data.
- Refer to the project documentation for troubleshooting database-related issues.
