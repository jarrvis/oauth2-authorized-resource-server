# oauth2-authorized-resource-server

Starter repo with following stack:
- Java Spring Boot 2.x.x
- Mongo DB
- Swagger (Open API)
Best to test: after running go to swagger html page, example: http://localhost:8080/swagger-ui.html

Repo contains resource server and authorization server (together as one component). 
This starter has only users as a resources but shows a pattern on how to create them.
User is able to register and log in as Admin, Customer or Partner.
Secondary log in option is using Facebook auth server (Spring Social).
App is using Spring profiles: dev and prod
