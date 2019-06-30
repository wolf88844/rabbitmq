package com.springmvc.rabbitmq.config;

import com.springmvc.rabbitmq.service.ConfirmCallBackListener;
import com.springmvc.rabbitmq.service.ReturnCallBackListener;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.port}")
    private String port;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.vhost}")
    private String vhost;

    @Value("${rabbitmq.channel-cache-size}")
    private String channelCacheSize;

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(Integer.valueOf(port));
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(vhost);
        connectionFactory.setChannelCacheSize(Integer.valueOf(channelCacheSize));
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate.ConfirmCallback confirmCallback(){
        return new ConfirmCallBackListener();
    }

    @Bean
    public RabbitTemplate.ReturnCallback returnCallback(){
        return new ReturnCallBackListener();
    }

    @Bean
    public AmqpTemplate amqpTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(confirmCallback());
        rabbitTemplate.setReturnCallback(returnCallback());
        return rabbitTemplate;
    }

    @Bean
    public AmqpAdmin amqpAdmin(){
        return new RabbitAdmin(connectionFactory());
    }


}
