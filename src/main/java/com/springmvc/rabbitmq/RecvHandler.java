package com.springmvc.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;



@Service("recvHandler")
@RabbitListener(containerFactory = "simpleMessageListenerContainer")
public class RecvHandler implements ChannelAwareMessageListener {


    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try{
            System.out.println("consumer--:"+message.getMessageProperties()+":"+new String(message.getBody()));
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }catch (Exception e){
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
        }

    }
}
