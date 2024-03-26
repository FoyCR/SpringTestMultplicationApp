package microservices.foytest.multiplication.challenge.services;

import microservices.foytest.multiplication.challenge.data.ChallengeAttemptRepository;
import microservices.foytest.multiplication.challenge.domain.ChallengeAttempt;
import microservices.foytest.multiplication.challenge.dto.ChallengeAttemptDTO;
import microservices.foytest.multiplication.challenge.publishers.AttemptEventPublisher;
// import microservices.foytest.multiplication.clients.GamificationServiceClient;
import microservices.foytest.multiplication.user.domain.User;
import microservices.foytest.multiplication.user.data.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ChallengeServiceTest {
    private ChallengeService challengeService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChallengeAttemptRepository attemptRepository;

    @Mock
    private AttemptEventPublisher attemptEventPublisher;
    // private GamificationServiceClient gameClient; //Old version using a synchronous REST API call

    @BeforeEach
    public void setUp() {
        challengeService = new ChallengeServiceImpl(userRepository, attemptRepository, attemptEventPublisher);
        //challengeService = new ChallengeServiceImpl(userRepository, attemptRepository, gameClient);
    }

    @Test
    public void checkCorrectAttemptTest() {
        // given
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "Foy", 3000);
        given(attemptRepository.save(any())).will(returnsFirstArg());
        //given(gameClient.sendAttempt(any())).willReturn(true);

        // when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        // then
        then(resultAttempt.isCorrect()).isTrue();

        //check for saving attempts
        verify(userRepository).save(new User("Foy"));
        verify(attemptRepository).save(resultAttempt);
        verify(attemptEventPublisher).AttemptVerified(resultAttempt);
        //verify(gameClient).sendAttempt(resultAttempt);
    }

    @Test
    public void checkWrongAttemptTest() {
        // given
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "Foy", 5000);
        given(attemptRepository.save(any())).will(returnsFirstArg());
        //given(gameClient.sendAttempt(any())).willReturn(true);

        //when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        //Then
        then(resultAttempt.isCorrect()).isFalse();

        //check for saving attempts
        verify(userRepository).save(new User("Foy"));
        verify(attemptRepository).save(resultAttempt);
        verify(attemptEventPublisher).AttemptVerified(resultAttempt);
        //verify(gameClient).sendAttempt(resultAttempt);
    }

    @Test
    public void checkExistingUserTest() {
        User existingUser = new User(1L, "Foy");
        given(userRepository.findByAlias("Foy")).willReturn(Optional.of(existingUser));
        given(attemptRepository.save(any())).will(returnsFirstArg());
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50, 60, "Foy", 5000);
        //given(gameClient.sendAttempt(any())).willReturn(true);

        //when
        ChallengeAttempt resultAttempt = challengeService.verifyAttempt(attemptDTO);

        //Then
        then(resultAttempt.isCorrect()).isFalse();

        //check for saving attempts
        then(resultAttempt.getUser()).isEqualTo(existingUser);
        verify(userRepository, never()).save(any());
        verify(attemptRepository).save(resultAttempt);
        verify(attemptEventPublisher).AttemptVerified(resultAttempt);
        //verify(gameClient).sendAttempt(resultAttempt);
    }

    @Test
    public void retrieveAttemptsTest() {
        //given
        User user = new User("Foy");
        ChallengeAttempt attempt1 = new ChallengeAttempt(1L, user, 50, 60, 5000, false);
        ChallengeAttempt attempt2 = new ChallengeAttempt(2L, user, 50, 60, 3000, true);
        List<ChallengeAttempt> lastAttempts = List.of(attempt1, attempt2);
        given(attemptRepository.findTop10ByUserAliasOrderByIdDesc("Foy")).willReturn(lastAttempts);

        //when
        List<ChallengeAttempt> lastAttemptsResult = challengeService.getAttemptsForUser("Foy");

        //then
        then(lastAttemptsResult).isEqualTo(lastAttempts);
    }
}
