package me.longli.demo.aoptest.service;

import java.util.concurrent.CompletableFuture;

public interface ServiceA {
    CompletableFuture<String> sayA();
}
