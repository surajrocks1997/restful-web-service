package com.example.restfulwebservice.controller;

import com.example.restfulwebservice.pojo.HelloWorldBean;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("hello")
    public String helloWorld() {
        return "Hello World";
    }

    @GetMapping("/helloBean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World");
    }

    @GetMapping("/helloBean/{name}")
    public HelloWorldBean pathVariable(@PathVariable String name) {
        return new HelloWorldBean("Hello World " + name);
    }

    @GetMapping("helloInternationalize")
    public String helloWorldInternationalized() {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message", null, "Default Message", locale);

        //        return "Hello World V2";
    }
}
