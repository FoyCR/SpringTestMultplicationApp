package microservices.foytest.multiplication.challenge.publishers;

import microservices.foytest.multiplication.challenge.domain.ChallengeAttempt;
import microservices.foytest.multiplication.challenge.dto.AttemptVerifiedEvent;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AttemptEventPublisher {
    // AmqpTemplate is just an interface that defines the AMQP standards. The underlying
    // implementation is RabbitTemplate, and it uses the JSON converter defined in AMQPConfiguration
    private final AmqpTemplate amqpTemplate;
    private final String attemptsTopicExchange;

    public AttemptEventPublisher(final AmqpTemplate amqpTemplate,
                                 @Value("${amqp.exchange.attempts}") final String attemptsTopicExchange) {
        this.amqpTemplate = amqpTemplate;
        this.attemptsTopicExchange = attemptsTopicExchange;
    }

    /**
     * Transforms an attempt into an event and sends it to the attempts topic exchange.
     *
     * @param attempt verified attempt
     */
    public void AttemptVerified(final ChallengeAttempt attempt) {
        AttemptVerifiedEvent event = buildEvent(attempt);
        //Routing key: 'attempt.correct' or 'attempt.failed'
        String routingKey = "attempt." + (attempt.isCorrect() ? "correct" : "failed");
        // We are using the default RabbitTemplate configuration via starter
        amqpTemplate.convertAndSend(attemptsTopicExchange, routingKey, event);
    }

    private AttemptVerifiedEvent buildEvent(final ChallengeAttempt attempt) {
        return new AttemptVerifiedEvent(
                attempt.getId(),
                attempt.isCorrect(),
                attempt.getFactorA(),
                attempt.getFactorB(),
                attempt.getUser().getId(),
                attempt.getUser().getAlias());
    }

}
