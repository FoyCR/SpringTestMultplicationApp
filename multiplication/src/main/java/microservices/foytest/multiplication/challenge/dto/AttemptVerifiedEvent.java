package microservices.foytest.multiplication.challenge.dto;

import lombok.Value;

/**
 * Used as message when an attempt was verified.
 */
@Value
public class AttemptVerifiedEvent {
    long attemptId;
    boolean correct;
    int factorA;
    int factorB;
    long userId;
    String userAlias;
}
