# Use OpenJDK 17 for the runtime image
FROM openjdk:17
ADD target/employee-management.jar employee-management.jar
# Run the application
ENTRYPOINT ["java", "-jar", "employee-management.jar"]
