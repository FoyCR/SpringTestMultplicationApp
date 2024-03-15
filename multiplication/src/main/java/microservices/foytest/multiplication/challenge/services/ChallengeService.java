package microservices.foytest.multiplication.challenge.services;

import microservices.foytest.multiplication.challenge.dto.ChallengeAttemptDTO;
import microservices.foytest.multiplication.challenge.domain.ChallengeAttempt;

import java.util.List;

public interface ChallengeService {
    /**
     * Verifies if an attempt coming from the presentation layer is correct or not
     *
     * @returns the resting ChallengeAttempt object
     */
    ChallengeAttempt verifyAttempt(ChallengeAttemptDTO resultAttempt);

    /**
     * @param userAlias the user's alias
     * @return a list of the lats 10 {@link ChallengeAttempt} records created by the user
     */
    List<ChallengeAttempt> getAttemptsForUser(String userAlias);
}
