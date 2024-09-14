# Rest API Documentation

* [AnswerEndpoint](#answerendpoint)
* [LogEndpoint](#logendpoint)
* [QuestionEndpoint](#questionendpoint)
* [QuoteEndpoint](#quoteendpoint)

## AnswerEndpoint

### Get All Answers

- **HTTP Method**: GET
- **Endpoint**: /answers
- **Allowed Roles**: admin, user
- **Produces**: JSON
- **Description**: Retrieves a list of all answers.

### Get Answer by ID

- **HTTP Method**: GET
- **Endpoint**: /answers/{id}
- **Allowed Roles**: admin, user
- **Produces**: JSON
- **Description**: Retrieves an answer by its unique identifier.

### Add Answer

- **HTTP Method**: POST
- **Endpoint**: /answers
- **Allowed Roles**: admin, user
- **Produces**: JSON
- **Consumes**: JSON
- **Description**: Adds a new answer to the database.

### Update Answer

- **HTTP Method**: PUT
- **Endpoint**: /answers/{id}
- **Allowed Roles**: admin, user
- **Produces**: JSON
- **Consumes**: JSON
- **Description**: Updates an existing answer with the specified ID.

### Delete Answer

- **HTTP Method**: DELETE
- **Endpoint**: /answers/{id}
- **Allowed Roles**: admin, user
- **Produces**: JSON
- **Description**: Deletes an answer by its unique identifier.

## LogEndpoint

### Get All Logs

- **HTTP Method**: GET
- **Endpoint**: /logs
- **Allowed Roles**: admin, logger, user
- **Produces**: JSON
- **Description**: Retrieves a list of all logs.

### Get Log by ID

- **HTTP Method**: GET
- **Endpoint**: /logs/{id}
- **Allowed Roles**: admin, logger
- **Produces**: JSON
- **Description**: Retrieves a log by its unique identifier.

### Add Log

- **HTTP Method**: POST
- **Endpoint**: /logs
- **Allowed Roles**: admin, logger
- **Produces**: JSON
- **Consumes**: JSON
- **Description**: Adds a new log to the database.

### Update Log

- **HTTP Method**: PUT
- **Endpoint**: /logs/{id}
- **Allowed Roles**: admin, logger
- **Produces**: JSON
- **Consumes**: JSON
- **Description**: Updates an existing log with the specified ID.

### Delete Log

- **HTTP Method**: DELETE
- **Endpoint**: /logs/{id}
- **Allowed Roles**: admin, logger
- **Produces**: JSON
- **Description**: Deletes a log by its unique identifier.

## QuestionEndpoint

### Get All Questions

- **HTTP Method**: GET
- **Endpoint**: /questions
- **Allowed Roles**: admin, user
- **Produces**: JSON
- **Description**: Retrieves a list of all questions.

### Get Question by ID

- **HTTP Method**: GET
- **Endpoint**: /questions/{id}
- **Allowed Roles**: admin, user
- **Produces**: JSON
- **Description**: Retrieves a question by its unique identifier.

### Add Question

- **HTTP Method**: POST
- **Endpoint**: /questions
- **Allowed Roles**: admin, user
- **Produces**: JSON
- **Consumes**: JSON
- **Description**: Adds a new question to the database.

### Update Question

- **HTTP Method**: PUT
- **Endpoint**: /questions/{id}
- **Allowed Roles**: admin, user
- **Produces**: JSON
- **Consumes**: JSON
- **Description**: Updates an existing question with the specified ID.

### Delete Question

- **HTTP Method**: DELETE
- **Endpoint**: /questions/{id}
- **Allowed Roles**: admin, user
- **Produces**: JSON
- **Description**: Deletes a question by its unique identifier.

### Get Random Questions

- **HTTP Method**: GET
- **Endpoint**: /questions/random/{amount}
- **Allowed Roles**: admin, user
- **Produces**: JSON
- **Description**: Retrieves a random selection of questions specified by the given amount.

### Delete All Questions

- **HTTP Method**: DELETE
- **Endpoint**: /questions/removeAll
- **Allowed Roles**: admin, user
- **Produces**: TEXT_PLAIN
- **Description**: Deletes all questions from the database. Returns "success" if successful; otherwise, returns an error message.

### Add All Questions

- **HTTP Method**: POST
- **Endpoint**: /questions/addList
- **Allowed Roles**: admin, user
- **Produces**: TEXT_PLAIN
- **Consumes**: JSON
- **Description**: Adds a list of questions to the database. Returns "success" if successful; otherwise, returns an error message.

## QuoteEndpoint

### Get All Quotes

- **HTTP Method**: GET
- **Endpoint**: /quotes
- **Allowed Roles**: admin, user
- **Produces**: JSON
- **Description**: Retrieves a list of all quotes.

### Get Quote by ID

- **HTTP Method**: GET
- **Endpoint**: /quotes/{id}
- **Allowed Roles**: admin, user
- **Produces**: JSON
- **Description**: Retrieves a quote by its unique identifier.

### Add Quote

- **HTTP Method**: POST
- **Endpoint**: /quotes
- **Allowed Roles**: admin, user
- **Produces**: JSON
- **Consumes**: JSON
- **Description**: Adds a new quote to the database.

### Update Quote

- **HTTP Method**: PUT
- **Endpoint**: /quotes/{id}
- **Allowed Roles**: admin, user
- **Produces**: JSON
- **Consumes**: JSON
- **Description**: Updates an existing quote with the specified ID. If the quote doesn't exist, it returns a message "Quote not found."

### Delete Quote

- **HTTP Method**: DELETE
- **Endpoint**: /quotes/{id}
- **Allowed Roles**: admin, user
- **Description**: Deletes a quote by its unique identifier.

### Add List of Quotes

- **HTTP Method**: POST
- **Endpoint**: /quotes/addList
- **Allowed Roles**: admin, user
- **Consumes**: JSON
- **Produces**: JSON
- **Description**: Adds a list of quotes to the database and returns the list of added quotes.

### Get Random Quote

- **HTTP Method**: GET
- **Endpoint**: /quotes/random
- **Allowed Roles**: admin, user
- **Produces**: JSON
- **Description**: Retrieves a random quote from the database.


# UserEndpoint

## Get All Users

- **HTTP Method**: GET
- **Endpoint**: /users
- **Allowed Roles**: admin
- **Produces**: JSON
- **Description**: Retrieves a list of all users.

## Get User by ID

- **HTTP Method**: GET
- **Endpoint**: /users/{id}
- **Allowed Roles**: admin
- **Produces**: JSON
- **Description**: Retrieves a user by their unique identifier.

## Login

- **HTTP Method**: GET
- **Endpoint**: /users/login/{user}
- **Allowed Roles**: admin, user, logger
- **Produces**: TEXT_PLAIN
- **Consumes**: TEXT_PLAIN
- **Description**: Logs a user in by their username. Updates the last login timestamp in the database. This doesnt really log you in instead it checks if the user data you logged in on the website is in the db

## Create User

- **HTTP Method**: POST
- **Endpoint**: /users
- **Allowed Roles**: admin
- **Consumes**: JSON
- **Produces**: TEXT_PLAIN
- **Description**: Creates a new user in the database.

## Update User

- **HTTP Method**: PUT
- **Endpoint**: /users/{id}
- **Allowed Roles**: admin
- **Produces**: JSON
- **Consumes**: JSON
- **Description**: Updates an existing user with the specified ID.

## Delete User

- **HTTP Method**: DELETE
- **Endpoint**: /users/{id}
- **Allowed Roles**: admin
- **Produces**: JSON
- **Description**: Deletes a user by their unique identifier.
