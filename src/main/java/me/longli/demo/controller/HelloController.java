package me.longli.demo.controller;

import me.longli.demo.retrytest.DemoRetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@Controller
public class HelloController {

    private final DemoRetryService demoRetryService;

    @Autowired
    public HelloController(DemoRetryService demoRetryService) {
        this.demoRetryService = demoRetryService;
    }

    @GetMapping("/sayHello")
    public String hello(@RequestParam("name") String name) {
        String resp = demoRetryService.sayHello(name);
        return resp;
    }
}
