package me.longli.demo.retrytest;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DemoRetryServiceImpl implements DemoRetryService {

    private final AtomicInteger index = new AtomicInteger(0);

    @Override
    public String sayHello(String name) {
        if (index.incrementAndGet() % 3 != 0) {
            throw new RuntimeException("index < 3");
        }
        return "Hello, " + name;
    }
}
