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

At API code level

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

### Entities

We will reuse the domain classes as Entities for the data layer using son JPA annotations, this decision is because we
want to keep the code simple. In a real scenario we could create new classes for the data layer and use mappers.

A new method in the ChallengeAttempt controller will be implemented to return the latest attempts by user.

API Code structure updated:

- microservices.foytest.multiplication.challenge
    - ChallengeController
        - ramdom (get method)
    - ChallengeAttemptController
        - attempt (post method)
        - attempts (get last attempts)

---

## User story 3

With this new story, some microservices things can be practiced.
Once the desired **gamification** aspects have been identified (points, leaderboard, badges), we group them into their
specific domain. This *gamification* domain will become a new microservice within our application.

### Design considerations

The reasoning behind this decision is as follows:

- The *Users* and *Challenge* domains are the core of the application. They need high availability, and maybe it will
  need to scale up if the user base increase. As long as the users are able to solve the challenges, the "business" can
  go on.
- For the *Gamification* domain we can accept that it performs slower, and even it can be stop working for some time.
- Considering the above we can benefit from having independently deployable units (microservices) in this scenario

To create the new Microservice we need to create a new Spring boot application to ensure that it wil be an independent
deployable unit.
The current Multiplication Service needs to be able to send the attempts to the new Gamification services which assigned
points, assigned badges and update the leaderboard.

