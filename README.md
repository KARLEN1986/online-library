# Online Library Java Spring Boot Project

Welcome to the Online Library project built with Java Spring Boot. This project demonstrates an example of using various technologies to create a web application for managing users, books, and recommendations in an online library.

## Technologies

- Java 17.
- Maven 3 for project build.
- MySQL 8 or higher for the database.
- JSON Web Tokens (JWT) for authentication and authorization.
- Lombok for reducing boilerplate code.
- MapStruct for object mapping.
- OpenCSV for handling CSV file operations.
- Hibernate Validator (Jakarta Validation) for bean validation.
- Spring doc for generating API documentation using OpenAPI (Swagger) specifications.

## Installation

1. Clone this repository to your computer.
2. Make sure you have Java and Maven installed.

## API Documentation with Swagger

This project uses Swagger to provide interactive API documentation. To access the Swagger UI, navigate to:

- Local: `http://localhost:8181/swagger-ui/index.html#/`

## API Resources

### Book Controller

#### Get all books

- Method: GET
- Path: `/api/v1/books`

#### Update book

- Method: PUT
- Path: `/api/v1/books`

#### Get BookDto by id

- Method: GET
- Path: `/api/v1/books/{id}`

#### Delete book

- Method: DELETE
- Path: `/api/v1/books/{id}`

### Recommendation Controller

#### Get book recommendations

- Method: GET
- Path: `/api/v1/recommendations/{userId}`

### Auth Controller

#### User registration

- Method: POST
- Path: `/api/v1/auth/register`

#### Refresh tokens

- Method: POST
- Path: `/api/v1/auth/refresh`

#### User login

- Method: POST
- Path: `/api/v1/auth/login`

### User Controller

#### Update user information

- Method: PUT
- Path: `/api/v1/users`

#### Get all User books

- Method: GET
- Path: `/api/v1/users/{id}/books`

#### Add book to user

- Method: POST
- Path: `/api/v1/users/{id}/books`

#### Get UserDto by id

- Method: GET
- Path: `/api/v1/users/{id}`

#### Delete user by id

- Method: DELETE
- Path: `/api/v1/users/{id}`


