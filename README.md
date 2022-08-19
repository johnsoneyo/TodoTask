# Todo App ( WIP ) 
[![Build Status](https://app.travis-ci.com/johnson3yo/TodoTask.svg?token=CAcYaTFWori8uznwxtxS&branch=main)](https://app.travis-ci.com/johnson3yo/TodoTask)
## About the Service

The service is just a simple Todo REST service. It uses an in-memory database (H2) to store the data. You can also do with a relational database like MySQL or PostgreSQL. If your database connection properties work, you can call some REST endpoints defined in com.simplesystem.todotask.controller.TodoController on port 8080. (see below)
TodoApp is also a springboot serverless application or deployable in any web container with integrations to **Quartz** that tracks created todos and schedules them based on their due dates, marking exceeded dates 
as `PAST_DUE`. 

## Libraries (Frameworks)
- [Quartz](https://www.quartz-scheduler.org) Autoscheduling and for clustered workers 
- [Model Mapper](http://modelmapper.org/) Simple Intelligent Object Mapping for `Http` patch/post   
- [Liquibase](https://www.liquibase.org/) Database Refactoring 
- [SpringBoot](https://spring.io/projects/spring-boot) Bootstrapping 
- [H2](http://www.h2database.com/html/main.html) Inmemory Database
- [Swagger]() The de-facto API documentation framework
## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)


## Running the application locally

Clone the application, open in IDE and execute the `main` method in the `com.simplesystem.todotask.TodotaskApplication` class from your IDE.

Alternatively from run the below command from shell terminal 
```
$ ./mvnw spring-boot:run
```
### Running with Docker
To run from docker execute the build docker file command (see below)
``` 
$ ./mvn clean package # To build todo.jar
$ docker build -t todoapp . # To Build docker image
$ docker run -p 8080:8080 todoapp:latest  # To run container
```

## Checkout Swagger 


Open a browser and key in URL or open from terminal like below:

```
$ open http://localhost:8080/swagger-ui/
```

## Running Tests

From Terminal you run the below command.

```
$ ./mvnw test
```

## Creating a Simple Todo
From the shell terminal, test with the below command

```
$ curl -X POST "http://localhost:8080/todos" -H "accept: application/json" -H "Content-Type: application/json" -d "{\"description\":\"Buy lemonade for kids\",\"dueDate\":\"2021-09-18T10:18:05.386Z\"}"
```

## Endpoints Examples

### API Resources

- [GET /todos](http://localhost:8080/swagger-ui/#/todo-controller/findAllUsingGET)
- [GET /todos/[id]](http://localhost:8080/swagger-ui/#/todo-controller/findOneUsingGET)
- [POST /todos](http://localhost:8080/swagger-ui/#/todo-controller/createUsingPOST)
- [PATCH /todos/[id]](http://localhost:8080/swagger-ui/#/todo-controller/modifyUsingPATCH)
