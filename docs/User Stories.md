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

---

## User Story 3

*as a user of the application, I want to feel motivated to engage in challenges daily, ensuring I don’t abandon the
application after a while. this will enable me to exercise my brain consistently and experience continual improvement.*

### Breakdown

This user story leads to a gamification of the application. To achieve this we agree to implement the following:

- Assign points for every correct answer (10 points)
- Create a Leader bord with the top scores showed on the page.
- Implements basic badges:
    - Bronze(10 correct attempts)
    - Silver (25 correct attempts)
    - Gold (50 correct attempts)
    - First Correct! (at the first correct attempt)
    - lucky Number! (when 69 is one of the factor)