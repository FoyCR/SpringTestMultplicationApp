# Gateway microservice

This module corresponds to the microservice that implements the **gateway pattern** to load balance the requests to the
other microservices. So we can have a multiple instances of the **multiplication** and **gamification** microservices
and the gateway will be entry point for them from the UI perspective.

This microservice will locally run on port 8000.