package microservices.foytest.multiplication.challenge.dto;

import lombok.Value;
import jakarta.validation.constraints.*;

@Value
public class ChallengeAttemptDTO {
    @Min(1) @Max(99)
    int factorA, factorB;
    @NotBlank
    String userAlias;
    @Positive(message = "The answer couldn't be negative.")
    int answer;
}
