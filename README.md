# NZPost coding exercise

This is a project to complete the NZPost coding exercise.

## Getting Started

### Prerequisites

JDK8 (http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
Neo4j server with APOC enabled. (https://neo4j.com/)
Maven (https://maven.apache.org/)
Node.js and npm (https://nodejs.org/)


### Installing

Copy both projects (nzpost and nzpost-client).
Create a graph on neo4j and load the data (test_data.cypher file inside nzpost projet) with the nodes and relationships. Update the application.properties inside nzpost project ( src/main/resoruces) with the correct user/password.
Execute mvn spring-boot:run inside the folder of nzpost project.
Execute npm run inside the folder of nzpost-client project.
Open the page http://localhost:3000 .

## Considerations

The Java project is using Spring Boot and Spring Neo4j-data to access the Neo4j graph database and recover the nodes/relationships.
It uses the APOC procedure dijkstra to find the shortest path between 2 nodes in a directional connected graph using a variable to calculate the distance/weight travelled.
This solution assumes that all stations (nodes) are reachable from any station (node) on the network (graph) and the tracks (relationships) are one-way.
The simple front-end was built using the React create app. It has a simple page that load all the possible source/destination and ask the user to select and display the shortest path
on a table. It connects to the java application using REST API to retrieve the possible stations and to find the shortest path.

This problems is basically a graph problem, with stations as nodes and tracks as relationships/edges. The option to use Neo4j as graph database to solve the problem took a good research time
to install/configure and start to use together with Spring boot. On the other hand this provides a easy way to visualize the graph/network, plus a set of tools to explore the graph.
On the reaming time it was the mapping of the nodes and relationships to java objects and the bootstrap of a simple front end to consume the API exposed.
There was a consideration for secure the endpoints, but this is not a production-ready application and is not sensible data, skipped for the limited time to develop.
To improve the project, include both projects on the same deployable and have a unique build for both projects together. Also create test cases for both projects and end-to-end tests to validate the solution.



## Authors

* **Jaziel Fernando Leandro**



## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
