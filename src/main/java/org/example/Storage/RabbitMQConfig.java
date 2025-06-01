package org.example.Storage;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "events.topic.exchange";

    public static final String CLIENT_QUEUE = "client-topic";
    public static final String ACCOUNT_QUEUE = "account-topic";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue clientQueue() {
        return new Queue(CLIENT_QUEUE, true);
    }

    @Bean
    public Queue accountQueue() {
        return new Queue(ACCOUNT_QUEUE, true);
    }

    @Bean
    public Binding clientBinding(Queue clientQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(clientQueue).to(topicExchange).with(CLIENT_QUEUE);
    }

    @Bean
    public Binding accountBinding(Queue accountQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(accountQueue).to(topicExchange).with(ACCOUNT_QUEUE);
    }

}
