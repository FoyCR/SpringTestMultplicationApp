package microservices.foytest.multiplication.challenge;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class ChallengeServiceTest {
    private ChallengeService challengeService;

    @BeforeEach
    public void setUp() {
        challengeService = new ChallengeServiceImpl();
    }

    @Test
    public void checkCorrectAttemptTest() {
        // given
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "Foy", 3000);

        // when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        // then
        then(resultAttempt.isCorrect()).isTrue();
    }

    @Test
    public void checkWrongAttemptTest() {
        // given
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "Foy", 5000);

        //when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        //Then
        then(resultAttempt.isCorrect()).isFalse();
    }
}
