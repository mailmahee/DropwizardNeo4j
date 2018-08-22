# Dropwizard service for Neo4J

1. Create an Employee node
This operation will take 2 params - a String and an int. The String value will populate the name property of the new Employee node, the int value will populate the emp_id value. 
Don't worry about data validation etc.

2. Return all Employee nodes to the client. Any format is fine.
For the actual implementation use Java (Spring boot, Dropwizard, Spark java etc) or Javascript (NodeJS + Express etc). 

For your API - Neo4J communication use a Bolt driver (https://neo4j.com/docs/develo per-manual/current/drivers/).

# Solution

Dropwizard framework is used to create REST service to talk to Neo4J.

Change the yml file as per your settings.


# To build the Project

mvn clean package -U

# To run the service

java -jar target/DropwizardNeo4j.jar server src/main/resources/config.yml

# For Accessing the WebUI

http://localhost:9090/swagger

# For simulating a POST call to create an Employee

curl -H "Content-Type: application/json" -X POST  --data '{ "emp_id": 1, "name": "Emil" }' http://localhost:9090/v1/neo4j/create_employee/

# For simulating a GET call to get a list of all the Employees

curl -H "Content-Type: application/json" -X GET  http://localhost:9090/v1/neo4j/all_employees

#For accessing the Health Check and various Metrics

http://localhost:9091/healthcheck?pretty=true

http://localhost:9091/metrics?pretty=true
