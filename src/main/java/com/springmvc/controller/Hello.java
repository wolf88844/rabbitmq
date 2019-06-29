package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Hello {

    @RequestMapping(value = "/hello.do")
    public String helloWorld(Model model){
        model.addAttribute("message","hello world!");
        return "HelloWorld";
    }
}
