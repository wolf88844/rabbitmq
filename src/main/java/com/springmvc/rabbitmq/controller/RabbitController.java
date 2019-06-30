package com.springmvc.rabbitmq.controller;

import com.springmvc.rabbitmq.service.PublishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Slf4j
@Controller
@RequestMapping("/rabbit/")
public class RabbitController {

    @Value("${rabbitmq.test.exchange}")
    private String exchange;

    @Value("${rabbitmq.test.queue}")
    private String queue;

    @Value("${rabbitmq.test.key}")
    private String key;


    @Resource
    private PublishService publishService;


    @RequestMapping(value = "testconfig",method = RequestMethod.GET)
    @ResponseBody
    public void testconfig() throws InterruptedException {
        String message = "currentTime:"+System.currentTimeMillis();
        System.out.println("testconfig---message:"+message);
        publishService.send(exchange,key,message);
        Thread.sleep(1000);
    }



    private static String exChange = "directExchange";

    @RequestMapping(value = "test1",method = RequestMethod.GET)
    @ResponseBody
    public void  test1() throws InterruptedException {
        String message = "currentTime:"+System.currentTimeMillis();
        System.out.println("test1---message:"+message);
        //exchange queue 正确 confirm回调 ack=true
        publishService.send(exChange,"CONFIRM_TEST",message);
        Thread.sleep(1000);
    }

    @RequestMapping(value = "test2",method = RequestMethod.GET)
    @ResponseBody
    public void  test2() throws InterruptedException {
        String message = "currentTime:"+System.currentTimeMillis();
        System.out.println("test2---message:"+message);
        //exchange 错误 queue 正确 confirm 回调 ack=false
        publishService.send(exChange+"NO","CONFIRM_TEST",message);
        Thread.sleep(1000);
    }

    @RequestMapping(value = "test3",method = RequestMethod.GET)
    @ResponseBody
    public void  test3() throws InterruptedException {
        String message = "currentTime:"+System.currentTimeMillis();
        System.out.println("test3---message:"+message);
        //exchange 正确 queue 错误 confirm 回调 ack=true return 回调 replyText:NO_ROUTE
        publishService.send(exChange,"",message);
        Thread.sleep(1000);
    }

    @RequestMapping(value = "test4",method = RequestMethod.GET)
    @ResponseBody
    public void  test4() throws InterruptedException {
        String message = "currentTime:"+System.currentTimeMillis();
        System.out.println("test4---message:"+message);
        //exchange 错误 queue 正确 confirm 回调 ack=false
        publishService.send(exChange+"NO","CONFIRM_TEST",message);
        Thread.sleep(1000);
    }
}
