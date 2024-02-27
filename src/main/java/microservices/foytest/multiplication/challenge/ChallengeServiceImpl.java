package microservices.foytest.multiplication.challenge;

import microservices.foytest.multiplication.user.User;
import org.springframework.stereotype.Service;

@Service
public class ChallengeServiceImpl implements ChallengeService {

    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attemptDTO) {
        //check if the attempt is correct
        boolean isCorrect = attemptDTO.getAnswer() == attemptDTO.getFactorA() * attemptDTO.getFactorB();

        //We do not use identifier at this point
        User user = new User(null, attemptDTO.getUserAlias());

        //Builds the domain object. Null id for now.
        ChallengeAttempt verifiedAttempt = new ChallengeAttempt(
                null,
                user.getId(),
                attemptDTO.getFactorA(),
                attemptDTO.getFactorB(),
                attemptDTO.getAnswer(),
                isCorrect);

        return verifiedAttempt;
    }
}
