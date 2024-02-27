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
