
# Transaction API
- Transaction Service runs on the port 8090
- Actuator health indicator
    http://localhost:8090/rest-transfer-service/actuator/health
  
## Prerequisites

What things you need to install the software and how to install them

```
- IntelliJ(Optional)
- JDK8+
- Maven

```
### H2- In Memory Database Scripts

   - Runs on start of the service
   
    - Database console url: 
      http://localhost:8090/rest-transfer-service/h2-console
   
    - /src/main/resources folder
      - schema.sql
      - data.sql
    
  
      
## Start the spring boot service from root folder of the project
  - mvn clean package
  - java -jar target/rest-transfer-service-0.0.1-SNAPSHOT.jar
   * ``(Or)``
  - mvn spring-boot:run
   * ``(Or)``
  - Can import in IntelliJ and run as main application by adding the Main file

## API
   Method:
   
   POST:
        - http://localhost:8090/rest-transaction-service/transfer \
   GET
        - http://localhost:8090/rest-transaction-service/account/{accountNumber}
  
  
## Swagger 

  Copy file "rest-transfer-service-swagger.yml" from
  
  * /src/main/resources/swagger/rest-transfer-service-swagger.yml 
      
   * Import or copy it in :  https://editor.swagger.io/
   

## Diagnostics 

1. Check for jdk version as it requires JDK 8
      - <terminal>> java -version
2. Run mvn clean package

       
## Contributing

 - Suhail Mir
  
## References
  - https://start.spring.io/

 
