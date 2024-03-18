package microservices.foy.gamification.challenge.dto;

import lombok.Value;

@Value
public class VerifiedAttemptDTO {
    long attemptId;
    boolean correct;
    int factorA;
    int factorB;
    long userId;
    String userAlias;
}
