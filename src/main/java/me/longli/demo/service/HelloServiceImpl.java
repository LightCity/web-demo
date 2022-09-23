package me.longli.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    private TimeShower timeShower;

    @Override
    public String sayHello(String name) {
        System.out.println("hello " + name);
        return "hello " + name;
    }

    @Override
    public void showTime() {
        timeShower.showTime();
    }
}
