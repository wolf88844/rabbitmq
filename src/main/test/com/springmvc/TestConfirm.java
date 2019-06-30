package com.springmvc;

import com.springmvc.rabbitmq.service.PublishService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestConfirm {
    @Resource
    private PublishService publishService;

    private static String exChange = "directExchange";

    @Test
    public void test1() throws InterruptedException {
        String message = "currentTime:"+System.currentTimeMillis();
        System.out.println("test1---message:"+message);
        publishService.send(exChange,"CONFIRM_TEST",message);
        Thread.sleep(1000);
    }
}
