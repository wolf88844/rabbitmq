package com.springmvc.rabbitmq.service;


import com.rabbitmq.client.Channel;
import com.springmvc.rabbitmq.config.RabbitMqConfig;
import com.springmvc.rabbitmq.config.RabbitTestQueueConfig;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RevService {

    @Autowired
    private RabbitMqConfig rabbitMqConfig;

    @Autowired
    private RabbitTestQueueConfig rabbitTestQueueConfig;

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(){
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(rabbitMqConfig.connectionFactory());
        listenerContainer.setQueues(rabbitTestQueueConfig.queue());
        listenerContainer.setExposeListenerChannel(true);
        listenerContainer.setMaxConcurrentConsumers(1);
        listenerContainer.setConcurrentConsumers(1);
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        listenerContainer.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                try{
                    System.out.println("listenerContainer--:"+message.getMessageProperties()+":"+new String(message.getBody()));
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
                }catch (Exception e){
                    e.printStackTrace();
                    channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
                }
            }
        });
        return listenerContainer;
    }
}
