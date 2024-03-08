# User Stories

## User Story 1

*As a user of the application, I want to be presented with a random multiplication problem so that I can solve it using
mental calculation and exercise my brain.*

### Breakdown

#### SubTasks:

- Create a basic service with business logic.
- Create a basic API to access this services (REST API).
- Create a basic web page to ask the user to solve the multiplication.

---

## User Story 2

*As a user of the application, I want to be able to access my previous attempts so that I can track my progress and
determine if my brain skills are improving over time.*

### Breakdown

- Store all the user attempts and have a way to query them per user.
- Create a new endpoint to get the latest attempts for a given user.
- Create a new service (business logic) to retrieve those attempts.
- Show the attempts' history to users on the web page after they send a new one.