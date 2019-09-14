# Spring boot MockMvc Demo

MockMvc approach is to not start the server at all, but test only the layer below that, where Spring handles the incoming HTTP request and hands it off to your controller. That way, almost the full stack is used, and your code will be called exactly the same way as if it was processing a real HTTP request, but without the cost of starting the server. To do that we will use Spring’s MockMvc, and we can ask for that to be injected for us by using the ```@AutoConfigureMockMvc``` annotation on the test case:

## Build with Maven

### Create the directory structure

In order to avoid unnecessary while execute the unit tests, manage DB by using different profiles. 

```
└── src
    └── main
        └── java
            └── com
                └── service
                    └── EmployeeServiceApplication.java
                        └── controller
                            └── EmployeeController.java
                        └── entity
                            └── Employee.java
                            └── EmployeeRepository.java
                        └── service
                            └── EmployeeService.java
            └── service
                └── application.properties
                └── application-dev.properties
                └── application-prod.properties

```
Along with other Spring Web dependencies, need to use ```spring-boot-starter-test```

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-test</artifactId>
   <scope>test</scope>
</dependency>
```
