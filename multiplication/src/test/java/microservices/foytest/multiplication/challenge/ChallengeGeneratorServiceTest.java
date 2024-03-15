package microservices.foytest.multiplication.challenge;

import microservices.foytest.multiplication.challenge.domain.Challenge;
import microservices.foytest.multiplication.challenge.services.ChallengeGeneratorService;
import microservices.foytest.multiplication.challenge.services.ChallengeGeneratorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;


import java.util.Random;

@ExtendWith(MockitoExtension.class)
public class ChallengeGeneratorServiceTest {
    private ChallengeGeneratorService challengeGeneratorService;
    @Spy //this creates a partial mock of the Random object, required to mock the nextInt() Method
    private Random random;

    @BeforeEach
    public void setUp() {
        challengeGeneratorService = new ChallengeGeneratorServiceImpl(random);
    }

    @Test
    public void generateRandomFactorIsBetweenExpectedLimits() {
        //89 is [max(99)  min(11)] range
        //When the arg is 89 it will return 20 the first call and 30 in the second call
        given(random.nextInt(89)).willReturn(20, 30);

        //When we generate a challenge
        Challenge challenge = challengeGeneratorService.randomChallenge();

        //then the challenge contains factor as expected, random number plus min(11)
        then(challenge).isEqualTo(new Challenge(31, 41));
    }

}
