package microservices.foytest.multiplication.challenge;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class ChallengeGeneratorServiceImpl implements ChallengeGeneratorService {
    private final Random random;

    ChallengeGeneratorServiceImpl() {
        this.random = new Random();
    }
    @Override
    public Challenge randomChallenge() {
        return null;
    }
}
