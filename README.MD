# Project structure

The project is divided into layers according to the principle of separation of concerns.
Each package represents different layer.

* entity - contain domain objects
* repository - represents DAO layer with proper repositories
* service - represents Service layer with business logic
* controller - represents Web layer with REST controllers

# Database

Application required "test_base" mysql schema on localhost with granted access for "admin" user with "admin" password by default.
To setup DB connection application.property should be changed

# Compiling

Compiling is done with maven. To compile simply run the command:

	mvn clean install

# Running

The project is setup with spring-boot, for convince a the script run.sh can be used

	./run.sh     -  To run the project
	./run.sh -c  -  To compile the project and then run it

The project starts on port 8085