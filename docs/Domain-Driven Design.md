# Domain Driven Design

## User Story 1

for the first user story. Let's identify and define the business entities:

- _Challenge_: Contains the two factors of a multiplication challenge.
- _User_: Identifies the person who will try to solve a _Challenge_.
- _Challenge attempt_: Represents the attempt from a _User_ to solve the operation from a _Challenge_.

**Domains**:

The _User_ entity will be in the **User Domain** and the _Challenge_ and _Challenge Attempt_ entities will be in the \*
\*Challenges Domain\*\*.

The relationships between the objects are as follows:

- _User_ and _Challenge_ are independent entities. They don't keep any references.
- _Challenge Attempt_ are always for a given _User_ and a given _Challenge_.

There could be many _Attempts_ for the same _Challenge_. Also, the same _User_ may create many _Attempts_ since they can
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

- **GET** _/challenges/random/_ will return a randomly generated challenge.
- **POST** _/attempts/_ will be the endpoint to send and attempt to solve a challenge.

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
specific domain. This _gamification_ domain will become a new microservice within our application.

### Design considerations

The reasoning behind this decision is as follows:

- The _Users_ and _Challenge_ domains are the core of the application. They need high availability, and maybe it will
  need to scale up if the user base increase. As long as the users are able to solve the challenges, the "business" can
  go on.
- For the _Gamification_ domain we can accept that it performs slower, and even it can be stop working for some time.
- Considering the above we can benefit from having independently deployable units (microservices) in this scenario

To create the new Microservice we need to create a new Spring boot application to ensure that it wil be an independent
deployable unit.
The current Multiplication Service needs to be able to send the attempts to the new Gamification services which assigned
points, assigned badges and update the leaderboard.

### Entities

In the new _Gamification_ domain we will add the following entities:

- **Leaderboard Position**: with a relation 1 to 1 with user.
- **Badge Card**: with a relation N to one with user (an User can have "n" badges)
- **Score Card**: with a relation 1 to N with user (an User can have "n" score cards) and with a relation 1 to 1 with attempt.

### User story 4

With this user story we are moving from tightly couple microservices to loosely couple microservices through the implementation of an Event-Driven Architecture using **RabbitMQ** as message broker.

### Design considerations

We are going to refactor our code to use a message broker to send the attempts to the gamification microservice instead of a REST API call. We will create an attempt _exchange_, of type **Topic**. The Multiplication microservice ows this exchange, it will be use two binding (routing) keys _attempt.correct_ and _attempt.failed_. On the other hand the Gamification microservice will declare a _queue_ with a _bind key_ that suits its requirements. In this case, this routing key is used as a filter to receive and process only correct attempts _attempt.correct_. If in the future we are interested in all the attempts (correct and failed) for instance for a new microservice of reporting, that microservice could create a new _queue_ and uses a binding key _attempt.\*_ (or _.#_) to consume both correct and wrong attempts.
