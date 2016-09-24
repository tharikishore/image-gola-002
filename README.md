## Executing this project
### Running from Command line 
1. From the root folder of the project, run `./gradlew appengineRun`. This will launch the application using Google development Server
1. Navigate to `http://localhost:8888` in the browser. 

### Running within a Docker container 
> This project is dockerized and can be run within a docker container

1. Execute `docker-compose up -d` to bring up the docker container
1. Execute `docker exec -it container-appengine /bin/bash` to attach terminal to the container.
1. Execute `./gradlew appengineRun` to launch the development server
1. Navigate to `http://localhost:9090` to access the application in the browser
