package microservices.foy.gamification.configuration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;


@Configuration
public class AMQPConfiguration {

    /**
     * Exchange for subscriber attempts
     *
     * @param exchangeName exchange name
     * @return exchange
     */
    @Bean
    public TopicExchange attemptsTopicExchange(@Value("${amqp.exchange.attempts}") final String exchangeName) {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    /**
     * Queue for subscriber attempts
     *
     * @param queueName queue name
     * @return Gamification Queue
     */
    @Bean
    public Queue gamificationQueue(@Value("${amqp.queue.gamification}") final String queueName) {
        return QueueBuilder.durable(queueName).build();
    }

    /**
     * Binding for subscriber attempts
     *
     * @param gamificationQueue gamification queue
     * @param attemptsExchange  attempts exchange
     * @return binding
     */
    @Bean
    public Binding correctAttemptsBinding(final Queue gamificationQueue, final TopicExchange attemptsExchange) {
        return BindingBuilder.bind(gamificationQueue).to(attemptsExchange).with("attempt.correct");
    }

    /**
     * This replaces the default MessageHandlerMethodFactory (using the default as baseline) with a custom JSON message converter
     *
     * @return custom message handler method factory
     */
    @Bean
    public MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        final MappingJackson2MessageConverter jsonConverter = new MappingJackson2MessageConverter();
        //to avoid having to use empty constructors for our event classes
        jsonConverter.getObjectMapper().registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        factory.setMessageConverter(jsonConverter);
        return factory;
    }

    /**
     * want the broker to notify subscribers when there are messages, so you’ll use an asynchronous that amqpTemplate not support,
     * but RabbitTemplate does. The RabbitListener is the simplest, most popular way to consume messages from a broker.
     *
     * @param messageHandlerMethodFactory custom message handler method factory
     * @return rabbit listener
     */
    @Bean
    public RabbitListenerConfigurer rabbitListenerConfigurer(final MessageHandlerMethodFactory messageHandlerMethodFactory) {
        return (c) -> c.setMessageHandlerMethodFactory(messageHandlerMethodFactory);
    }


}
