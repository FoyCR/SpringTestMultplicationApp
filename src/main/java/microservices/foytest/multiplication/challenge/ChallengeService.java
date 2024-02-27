package microservices.foytest.multiplication.challenge;

public interface ChallengeService {
    /**
     * Verifies if an attempt coming from the presentation layer is correct or not
     *
     * @returns the resting ChallengeAttempt object
     */
    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO resultAttempt);
}
