package microservices.foytest.multiplication.challenge.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.foytest.multiplication.challenge.dto.ChallengeAttemptDTO;
import microservices.foytest.multiplication.challenge.data.ChallengeAttemptRepository;
import microservices.foytest.multiplication.challenge.domain.ChallengeAttempt;
import microservices.foytest.multiplication.clients.GamificationServiceClient;
import microservices.foytest.multiplication.user.data.User;
import microservices.foytest.multiplication.user.data.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChallengeServiceImpl implements ChallengeService {
    private final UserRepository userRepository;
    private final ChallengeAttemptRepository attemptRepository;
    private final GamificationServiceClient gameClient;

    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attemptDTO) {
        //Check if the User already exist for that alias, otherwise create it
        User user = getOrCreateUser(attemptDTO.getUserAlias());

        //check if the attempt is correct
        boolean isCorrect = attemptDTO.getAnswer() == attemptDTO.getFactorA() * attemptDTO.getFactorB();

        //save the attempt
        ChallengeAttempt storedAttempt = saveAttempt(user, attemptDTO, isCorrect);

        //send the attempt to the gamification service
        sendAttemptToGameService(storedAttempt);

        return storedAttempt;
    }

    @Override
    public List<ChallengeAttempt> getAttemptsForUser(String userAlias) {
        return attemptRepository.findTop10ByUserAliasOrderByIdDesc(userAlias);
    }

    private User getOrCreateUser(String alias) {
        return userRepository.findByAlias(alias)
                .orElseGet(() -> {
                    log.info("Creating new user with alias {}", alias);
                    return userRepository.save(new User(alias));
                });
    }

    private ChallengeAttempt saveAttempt(User user, ChallengeAttemptDTO dto, boolean isCorrect) {
        //Builds the domain object. Null id for now.
        ChallengeAttempt attempt = new ChallengeAttempt(
                null,
                user,
                dto.getFactorA(),
                dto.getFactorB(),
                dto.getAnswer(),
                isCorrect);
        return attemptRepository.save(attempt);
    }

    private void sendAttemptToGameService(ChallengeAttempt attempt) {
        boolean status = gameClient.sendAttempt(attempt);
        log.info("Gamification service response: {}", status);
    }
    
}
