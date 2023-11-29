# beat-code-fullstack
BACKEND  

1. set up database  
`mysql -u root -p < project.sql` (or just run project.sql in your MySQL)  
`cd src/main/resources`  
edit `application.yml` with your MySQL password  

2. set up backend project
`mvn install clean` (only rerun when changes to dependencies made)  
`mvn spring-boot:run`  

backend will be running on `http://localhost:8080`  
go to beat-code-fullstack main branch for fullstack `git checkout main`  
