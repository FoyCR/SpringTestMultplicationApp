package microservices.foytest.multiplication.challenge.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.foytest.multiplication.challenge.dto.ChallengeAttemptDTO;
import microservices.foytest.multiplication.challenge.data.ChallengeAttemptRepository;
import microservices.foytest.multiplication.challenge.domain.ChallengeAttempt;
import microservices.foytest.multiplication.user.User;
import microservices.foytest.multiplication.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChallengeServiceImpl implements ChallengeService {
    private final UserRepository userRepository;
    private final ChallengeAttemptRepository attemptRepository;

    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attemptDTO) {
        //Check uf the User already exist for that alias, otherwise create it
        User user = userRepository.findByAlias(attemptDTO.getUserAlias())
                .orElseGet(() -> {
                    log.info("Creating new user with alias {}", attemptDTO.getUserAlias());
                    return userRepository.save(new User(attemptDTO.getUserAlias()));
                });

        //check if the attempt is correct
        boolean isCorrect = attemptDTO.getAnswer() == attemptDTO.getFactorA() * attemptDTO.getFactorB();

        //Builds the domain object. Null id for now.
        ChallengeAttempt verifiedAttempt = new ChallengeAttempt(
                null,
                user,
                attemptDTO.getFactorA(),
                attemptDTO.getFactorB(),
                attemptDTO.getAnswer(),
                isCorrect);
        ChallengeAttempt storedAttempt = attemptRepository.save(verifiedAttempt);
        return storedAttempt;
    }

    @Override
    public List<ChallengeAttempt> getAttemptsForUser(String userAlias) {
        return attemptRepository.findTop10ByUserAliasOrderByIdDesc(userAlias);
    }
}
