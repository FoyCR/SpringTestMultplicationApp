package microservices.foytest.multiplication.challenge.publishers;

import microservices.foytest.multiplication.challenge.domain.ChallengeAttempt;
import microservices.foytest.multiplication.challenge.dto.AttemptVerifiedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import microservices.foytest.multiplication.user.domain.User;

import static org.assertj.core.api.BDDAssertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AttemptEventPublisherTest {
    private AttemptEventPublisher attemptEventPublisher;

    @Mock
    private AmqpTemplate amqpTemplate;

    @BeforeEach
    public void setUp() {
        attemptEventPublisher = new AttemptEventPublisher(amqpTemplate, "attemptsTopicExchange");
    }


    // this will transform in two tests: one for correct and one for failed attempt
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void sendsAttemptVerifiedEvent(boolean isCorrect) {
        //given
        //These captors is used to capture the arguments passed to the convertAndSend method
        var exchangeCaptor = ArgumentCaptor.forClass(String.class);
        var routingKeyCaptor = ArgumentCaptor.forClass(String.class);
        var eventCaptor = ArgumentCaptor.forClass(AttemptVerifiedEvent.class);

        ChallengeAttempt attempt = buildAttempt(isCorrect);

        //when
        attemptEventPublisher.AttemptVerified(attempt);

        //then
        verify(amqpTemplate).convertAndSend(exchangeCaptor.capture(), routingKeyCaptor.capture(), eventCaptor.capture());
        then(exchangeCaptor.getValue()).isEqualTo("attemptsTopicExchange");
        then(routingKeyCaptor.getValue()).isEqualTo("attempt." + (isCorrect ? "correct" : "failed"));
        then(eventCaptor.getValue()).isEqualTo(buildEvent(isCorrect));
    }

    // build a challenge attempt test
    private ChallengeAttempt buildAttempt(boolean isCorrect) {
        User user = new User(1L, "Foy");
        return new ChallengeAttempt(1L, user, 30, 40, isCorrect ? 1200 : 1300, isCorrect);
    }

    //build a AttemptVerifiedEvent test
    private AttemptVerifiedEvent buildEvent(boolean isCorrect) {
        return new AttemptVerifiedEvent(1L, isCorrect, 30, 40, 1L, "Foy");
    }
}
