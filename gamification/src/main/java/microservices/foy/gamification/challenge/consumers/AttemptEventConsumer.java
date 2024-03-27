package microservices.foy.gamification.challenge.consumers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservices.foy.gamification.challenge.dto.AttemptVerifiedEvent;
import microservices.foy.gamification.game.services.GameService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AttemptEventConsumer {
    private final GameService gameService;

    @RabbitListener(queues = "${amqp.queue.gamification}")
    //this triggers the async message processing when a message is received
    public void handleAttemptVerifiedEvent(AttemptVerifiedEvent event) {
        log.info("Received attempt verified event: {}", event.getAttemptId());
        try {
            gameService.newAttemptForUser(event);
        } catch (final Exception e) {
            log.error("Error processing attempt verified event", e);
            //avoid the event to be re-queued and reprocessed
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
