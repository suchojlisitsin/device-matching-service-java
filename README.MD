# Running the project

## Prerequisites
- Java 21
- Maven
- Docker
- Aerospike instance

## Configuration
The project uses Aerospike and expects the following:
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
### Terminal
```bash
mvn spring-boot:run
```



## Tests
### IDE
### Terminal
```bash
mvn test
```
