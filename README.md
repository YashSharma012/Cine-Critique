# Cine Critique

This project is a backend system for managing **movie reviews and ticket booking functionalities**. It provides RESTful APIs for adding movies, adding reviews, managing shows and theaters, booking tickets, and managing user accounts.

## Features

- **Movie Management:** APIs for adding and retrieving movie details.
- **Review Management:** APIs for adding and retrieving movie reviews.
- **Show Management:** APIs for adding shows, searching for shows by various criteria such as city, movie name, and theater name.
- **Theater Management:** APIs for adding and retrieving theater details.
- **Ticket Booking:** APIs for booking tickets.
- **User Management:** APIs for user signup and retrieval.

## Technologies Used

- **Spring Boot:** A powerful framework for building Java-based applications.
- **Spring Security:** Provides security features for the application.
- **RESTful APIs:** Utilizes REST architecture for communication.
- **Hibernate:** An object-relational mapping tool for managing database operations.

## API Endpoints

### Movie Controller

- `POST /movie/add`: Add a new movie.
- `GET /movie/{id}`: Get movie details by ID.
- `GET /movie/title`: Get movie details by title.

### Review Controller

- `POST /review/add`: Add a new review.
- `GET /review/{id}`: Get review details by ID.

### Show Controller

- `POST /show/add`: Add a new show.
- `GET /show/search`: Search for shows by city, movie name, and theater name.

### Theater Controller

- `POST /theater/add`: Add a new theater.
- `GET /theater/{id}`: Get theater details by ID.

### Ticket Controller

- `POST /ticket/book`: Book a ticket.
- `GET /ticket/{id}`: Get ticket details by ID.

### User Controller

- `POST /user/signup`: Sign up a new user.
- `GET /user/{id}`: Get user details by ID.

## Security Configuration

The application utilizes Spring Security for access control. The following rules are applied:

- `/movie/**`, `/theater/**`, `/show/add`: Restricted to users with admin authority.
- `/review/**`, `/show/search`, `/movie/title`, `/ticket/**`: Restricted to users with user authority.
- Any other requests require authentication.

## Setup

1. Clone this repository.
2. Ensure you have Java and Maven installed.
3. Build the project using Maven: `mvn clean install`.
4. Run the application: `java -jar target/<name_of_jar_file>.jar`.
5. Access the API endpoints using an HTTP client or integrate them into your frontend application.

## Contributors

- Yash Sharma
- Curious to include your name here.

## Additional DTO Information

Below are the DTO (Data Transfer Object) classes used in the project:

- **BookingDTO:** Represents a booking request with seat numbers, user ID, show ID, and seat type.
- **MovieDTO:** Represents movie details including ID, title, genre, rating, and list of reviews.
- **ReviewDTO:** Represents a movie review with movie review text, rating, and movie ID.
- **ShowDTO:** Represents show details including ID, show time, movie ID, theater ID, creation and update timestamps, movie and theater DTOs, and list of show seat DTOs.
- **ShowSeatDTO:** Represents details of a show seat including ID, seat number, rate, seat type, booking status, and booking timestamp.
- **TheaterDTO:** Represents theater details including ID, name, regular and recliner seat counts, city, address, and list of show DTOs.
- **TicketDTO:** Represents ticket details including ID, allotted seats, amount, booking timestamp, and associated show DTO.
- **TicketMessage:** Represents ticket message details including user name, mobile, email, associated show DTO, and list of show seat DTOs.
- **UserDTO:** Represents user details including ID, username, password, role, mobile, email, and list of ticket DTOs.
