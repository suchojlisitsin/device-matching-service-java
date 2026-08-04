# Running the project

## Prerequisites
- Java 21
- Maven
- Docker
- Aerospike instance

## Configuration
The project uses Aerospike and expects the following in application.yaml:
```yaml
spring:
  aerospike:
    hosts: localhost:4500

  data:
    aerospike:
      namespace: test
      scans-enabled: true
      create-indexes-on-startup: true
      send-key: true
```
Change host, port and namespace to fit your configuration.

## Project
### IDE
Start the Aerospike instance.
Running DeviceMatcherApplication.java will run the project.
### Terminal
```bash
mvn spring-boot:run
```



## Tests
### IDE
The tests can be ran seperately:
- UserAgentParserTest is a JUnit Unit test
- MatcherServiceTest is a Mockito Unit test
- DeviceControllerTest is a MockMvc test for the REST API
- ServiceIntegrationTest is an integration test (Aerospike instance must be already running)
### Terminal
```bash
mvn test
```
