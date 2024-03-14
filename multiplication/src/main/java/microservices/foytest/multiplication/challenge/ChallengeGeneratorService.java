package microservices.foytest.multiplication.challenge;

public interface ChallengeGeneratorService {
    /**
     * @return a randomly generated multiplication challenge with factors between 11 and 99
     */
    Challenge randomChallenge();
}
