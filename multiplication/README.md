# Multiplication microservice

This module corresponds to the first microservice within the application of the multiplication challenge.
It is composed of the domains Users and Challenge.
It has two Endpoints:
- Challenge:
  - random: GET method -> generates a new random multiplication challenge
- Attempts
  - attempt: POST method -> receives the user solution attempt by the user
  - attempts: GET method -> gets the latest attempts by user alias.

This microservice will locally run on port 8080.