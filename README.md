# DoctorAnywhereApp

The application is a Spring Boot Application having REST API calls for Patient's data.
Each Patient has following attributes-
1. First name
2. Last name
3. Email
4. Phone number
5. Gender
6. Address information

A patient can have multiple addresses which can be retreived using the Address api.

Authorization is maintained using the Firebase and the application supports login-logout using firebase auth.
The application can be accessed using http://localhost:8080
For accessing the REST APIs, please use http://localhost:8080/swagger-ui.html

The login page takes email address and password from user as input and authorizes it using Firebase auth mechanism.
New user has to click on SignUp link which is used for signUp functionality.
Upon successful login, the user can view existing patients and user can add new patients/modify existing patients/ add more address to existing patient/ delete the patients from the database.
Logout option is also available to log out. After log out, the user has to login again in order to view the patient's information.


Basic flow- 
Login User -> View existing patients -> Edit existing patient's data / delete existing patient record / add more patients -> Logout.


Setup application locally -
1. You must have Java installed on your machine and IDE should have Gradle installed.
2. Import the project as Gradle project in your IDE.
3. Run the main class -  Application.java
4. You can view in logs, H2 database will gets initialized and you can check the h2 console using http://localhost:8080/h2 
username- admin
password- admin
db name- jdbc:h2:mem:patientsdb (in memory db)
the scripts for schema can be viewed from schema.sql and the predefined patient records can be viewed from data.sql.
5. You can access the application using http://localhost:8080





