package microservices.foytest.multiplication.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

@Configuration
public class AMQPConfiguration {
    // Creates the topic exchange for the gamification service via configuration value
    @Bean
    public TopicExchange attemptsTopicExchange(@Value("${amqp.exchange.attempts}") final String exchangeName) {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    // Overrides the default Java object serializer by a JSON object serializer (avoiding several pitfall
    // when using Java objects as message payloads)
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
