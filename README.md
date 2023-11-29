# beat-code-fullstack
BACKEND  
all `cd` commands given starting from top (beat-code-fullstack) directory  

1. set up database  
`cd beat-code-back`  
`mysql -u root -p < project.sql` (or just run project.sql in your MySQL)  
`cd beat-code-back/beat-code-back/src/main/resources`  
edit `application.yml` with your MySQL password  

2. set up backend project
`cd beat-code-back/beat-code-back` (you should see pom.xml file)   
`mvn install clean` (only rerun when changes to dependencies made)  
`mvn spring-boot:run`

backend will be running on `http://localhost:8080`  
go to beat-code-fullstack main branch for fullstack `git checkout main`  
