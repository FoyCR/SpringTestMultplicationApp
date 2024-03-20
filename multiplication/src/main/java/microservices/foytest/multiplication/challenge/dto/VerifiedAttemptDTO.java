package microservices.foytest.multiplication.challenge.dto;

import lombok.Value;

/**
 * Used as contract between the gamification service and the challenge service.
 */
@Value
public class VerifiedAttemptDTO {
    long attemptId;
    boolean correct;
    int factorA;
    int factorB;
    long userId;
    String userAlias;
}
