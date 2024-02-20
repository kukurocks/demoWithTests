## Demo with tests
### About

---
A Java Enterprise project I created as part of the curriculum at Hillel IT School.
The idea of the project is a CRUD API for managing employees.
The project is written in the REST architectural style.

This reliable Java project offers `five well-defined entities with diverse relationships`, 
leveraging the DTO pattern for efficient data transfer. 
It boasts seamless database versioning through both `Flyway and Liquibase`, 
empowering you to choose the optimal solution for your needs. 
It utilizes `Docker Compose` for containerized deployment, 
ensuring consistent and portable environments. 
Additionally, it handles `email integration and secure file storage`, 
while providing comprehensive `OpenApi documentation`
and `spring-security-based jdbcAuth` implementation for robust authentication.

### Documentation

---
The documentation is written in OpenApi and uses SwaggerUI.
To read it, launch the project and follow the link: [Documentation](http://localhost:8088/swagger-ui.html).

### Quick start

---
Quick guide to run app.

1. Install Docker. [Docker Docs](https://docs.docker.com/get-docker/).
2. Install Maven. [Download Maven](https://maven.apache.org/download.cgi).
3. Run Docker (required for maven plugin to work).
4. Clone the repository `git clone https://github.com/kukurocks/demoWithTests.git`.
5. Open file `application.yml` and set `create` to field `spring: jpa: hibernate: ddl-auto:` for the first run. Next
   time you run it, put `update`.
6. To work with e-mail, configure the SMPT server you need and configure it in the application.yaml file.
7. Open bash/cmd in project directory.
8. Run `mvn clean install -DskipTests=true`
9. Run `docker-compose up -d`
10. Use [app](http://localhost:8088/) or go to docs with [link](http://localhost:8088/swagger-ui.html).

