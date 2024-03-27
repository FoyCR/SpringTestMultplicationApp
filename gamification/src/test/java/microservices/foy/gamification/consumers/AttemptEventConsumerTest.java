package microservices.foy.gamification.consumers;


import microservices.foy.gamification.challenge.consumers.AttemptEventConsumer;
import microservices.foy.gamification.challenge.dto.AttemptVerifiedEvent;
import microservices.foy.gamification.game.services.GameService;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class AttemptEventConsumerTest {
    private AttemptEventConsumer attemptEventConsumer;
    @MockBean
    private GameService gameService;

    @BeforeEach
    public void setUp() {
        attemptEventConsumer = new AttemptEventConsumer(gameService);
    }

    @Test
    public void handleAttemptVerifiedEventSuccessfully() {
        //given
        AttemptVerifiedEvent event = buildEvent();
        //when
        attemptEventConsumer.handleAttemptVerifiedEvent(event);
        //then
        verify(gameService, times(1)).newAttemptForUser(event);
    }

    @Test
    public void handleAttemptVerifiedEventFailed() {
        //given
        AttemptVerifiedEvent event = buildEvent();
        given(gameService.newAttemptForUser(any())).willThrow(new RuntimeException("Test error"));

        //when
        ThrowableAssert.ThrowingCallable attemptEventHandling = () ->
                attemptEventConsumer.handleAttemptVerifiedEvent(event);

        //then
        // Verify that AmqpRejectAndDontRequeueException is thrown
        thenThrownBy(attemptEventHandling).isInstanceOf(AmqpRejectAndDontRequeueException.class);

        verify(gameService, times(1)).newAttemptForUser(event);
    }

    private AttemptVerifiedEvent buildEvent() {
        return new AttemptVerifiedEvent(1L, true, 10, 10, 1L, "Foy");
    }
}
