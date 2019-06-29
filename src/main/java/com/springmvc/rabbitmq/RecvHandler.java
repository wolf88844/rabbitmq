package com.springmvc.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;


@Component
@RabbitListener(containerFactory = "simpleMessageListenerContainer")
public class RecvHandler implements ChannelAwareMessageListener {

    private AtomicInteger count =new AtomicInteger(0);

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        count.incrementAndGet();
        String msg = new String(message.getBody(), "UTF-8");
        System.out.println("message is " + msg + " count is :" + count);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
