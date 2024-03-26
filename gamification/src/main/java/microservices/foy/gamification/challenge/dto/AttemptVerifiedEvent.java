package microservices.foy.gamification.challenge.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class AttemptVerifiedEvent {
    long attemptId;
    boolean correct;
    @Min(10) @Max(99)
    int factorA;
    @Min(10) @Max(99)
    int factorB;
    @NotNull
    long userId;
    @NotBlank
    String userAlias;
}
