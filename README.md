<a><img  src="https://media.discordapp.net/attachments/678319401375891474/715661751588356116/unknown.png" title="DonJoe" alt="Don Joe"></a>

# DonJoe - Sample Login Project in Springboot

> A barebones login and persistent session web application, done with Java 11 Springboot

Technologies Used
-
- Java 11
- SpringBoot, JPA
- Thymeleaf
- Maven
- JUnit
- HTML5 & CSS3
- mySQL, JDBC connector
- SQL Injection Prevention
- Git
- IntelliJ (Development Environment)
 - Hosted/Deployed WebApp
 - Hosted/Deployed Database
 - Environment-based configuration

# Installation
*The provided source code has been written and tested in Jetbrains IntelliJ. It is not guaranteed to work as-is when imported with other IDEs*

The Web App can be run in 3 ways:
-
- Run Locally - simply compile and run the code via your IDE. The website can be accessed via `localhost:portNumber`. By Default, we web app is running on port 8080 ([localhosst:8080](localhost:8080))
- Packaged as a jar file - Run `mvn package` in the terminal to create a jar package which then can be run locally or on a server
- Deployed to a web hosting *(the exact method depends on the hosting service)*

The Web App is configured via a `application.properties` file. 
The following variables can be edited:
- Website port (the post on which the application is listening)
- Database Connection Information *(Username, password, url)*

All variables have to be configured properly in order for the application to work

License
-
The Software is released under an [MIT license](https://opensource.org/licenses/MIT)