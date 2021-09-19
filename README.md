# File Scanner Service

## Secret Detect

To test this application:

1. Create a folder with files that should be scanned
2. To run the application, run the following command in a terminal window (in the complete) directory:
   __./mvnw spring-boot:run__
3. Now run the service with curl (in a separate terminal window), by running the following command:
   __curl -d '{"localPath": "[local-path]"}' -H 'Content-Type: application/json' localhost:8080/secret-detect__
