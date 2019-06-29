package com.springmvc.rabbitmq.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("/rabbit/")
public class RabbitController {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @RequestMapping(value = "send",method = RequestMethod.GET)
    @ResponseBody
    public void  rabbit(){
        HashMap<String,String> map = new HashMap<>();
        map.put("id","1");
        map.put("name","pig");
        rabbitTemplate.convertAndSend("directExchange","queuekey", JSONObject.toJSONString(map.toString()));
    }
}
