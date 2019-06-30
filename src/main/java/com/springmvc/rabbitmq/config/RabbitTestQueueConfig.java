package com.springmvc.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:rabbitmq.properties")
public class RabbitTestQueueConfig {
    @Value("${rabbitmq.test.exchange}")
    private String exchange;

    @Value("${rabbitmq.test.queue}")
    private String queue;

    @Value("${rabbitmq.test.key}")
    private String key;

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue queue(){
        return QueueBuilder.durable(queue).build();
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(this.queue()).to(this.directExchange()).with(key);
    }
}
