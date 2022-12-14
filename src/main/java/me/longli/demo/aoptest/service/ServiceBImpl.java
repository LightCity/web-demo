package me.longli.demo.aoptest.service;

import me.longli.demo.aoptest.aop.MyAnnotation;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ServiceBImpl implements ServiceB {

    @MyAnnotation(someInfo = "ServiceBImpl_sayB")
    @Async
    @Override
    public CompletableFuture<String> sayB() {
        System.out.println("ServiceBImpl#sayB() 被调用");
        return CompletableFuture.completedFuture("ServiceBImpl");
    }
}
