package microservices.foytest.multiplication.challenge.services;

import microservices.foytest.multiplication.challenge.domain.Challenge;

public interface ChallengeGeneratorService {
    /**
     * @return a randomly generated multiplication challenge with factors between 11 and 99
     */
    Challenge randomChallenge();
}
