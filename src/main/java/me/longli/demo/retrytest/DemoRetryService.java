package me.longli.demo.retrytest;

import org.springframework.retry.annotation.Retryable;

public interface DemoRetryService {

    @Retryable
    String sayHello(String name);
}
