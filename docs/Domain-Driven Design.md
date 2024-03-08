# Domain Driven Design

## User Story 1

for the first user story. Let's identify and define the business entities:

- *Challenge*: Contains the two factors of a multiplication challenge.
- *User*: Identifies the person who will try to solve a *Challenge*.
- *Challenge attempt*: Represents the attempt from a *User* to solve the operation from a *Challenge*.

**Domains**:

The *User* entity will be in the **User Domain** and the *Challenge* and *Challenge Attempt* entities will be in the *
*Challenges Domain**.

The relationships between the objects are as follows:

- *User* and *Challenge* are independent entities. They don't keep any references.
- *Challenge Attempt* are always for a given *User* and a given *Challenge*.

There could be many *Attempts* for the same *Challenge*. Also, the same *User* may create many *Attempts* since they can
use the web app as many times as they want.

**Code**:

At a code level will be created a packages for **Domains** and classes for entities:

- microservices.foytest.multiplication.user
    - User.java
- microservices.foytest.multiplication.challenge
    - Challenge.java
    - ChallengeAttempt.java

### About the presentation layer

The presentation layer for this first story will be a REST API. So we will need:

- An endpoint to get a random-medium complexity multiplication.
- An endpoint to send the answer for a given multiplication.

So are the design of our endpoints using REST will be

- **GET** */challenges/random/* will return a randomly generated challenge.
- **POST** */attempts/* will be the endpoint to send and attempt to solve a challenge.

At code level

- microservices.foytest.multiplication.challenge
    - ChallengeController
        - ramdom (get method)
    - ChallengeAttemptController
        - attempt (post method)

---

## User story 2

For the second user story we keep the same domains an entities, but we are going to add the data layer in order to store
the attempts data.
To achieve this, we made the following decisions:

- Use H2 database because is simple and small, it can be embedded within your application.
- Use Hibernate as ORM to map the Java objects to SQL records.
- Use the JPA (Java persistence API) instead of native API in Hibernate to map the objects to database records.
- 