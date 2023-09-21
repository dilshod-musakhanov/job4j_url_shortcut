### URL Shortcut Service Project

This project is a web service for shortening URLs and providing usage statistics.
Offers the following functionality:

    Site Registration
        The service allows different websites to register within the system.
        Each website is issued a unique login and password.
        To register a site, send a POST request with a JSON object to /registration.

    Authentication
        Authentication is performed using JWT (JSON Web Token).
        Users can obtain a JWT token by sending a POST request with their login and password.
        To authenticate, send a POST request to /login.

    URL Registration
        After registration, a website can send URLs to the service for conversion.
        Upon conversion, the website receives a unique code for each URL.
        To register a URL, send a POST request with a JSON object to /convert.

    Redirection
        When a URL code is sent to the service, it responds with a redirection to the corresponding URL.
        Redirection is performed without authentication.
        To perform redirection, send a GET request to /redirect/{code}.

    Statistics
        The service keeps track of the number of accesses to each URL.
        Statistical data is stored in a database.
        You can retrieve statistics for all URLs by sending a GET request to /statistic.

Getting Started

Clone the Repository

    git clone <repository URL>
    cd job4j_url_shortcut

Configure the Database

    Modify the database settings (e.g., URL, username, and password) in the application.properties file.

Run the Application

    ./mvnw spring-boot:run

Technologies Used

    Java 17
    Spring Boot 2
    Spring Data JPA
    Spring Security
    JWT (JSON Web Token)
    PostgreSQL


**Author:** Dilshod Musakhanov

**Contact:** musakhanov@yahoo.com

**Created:** 09/21/2023