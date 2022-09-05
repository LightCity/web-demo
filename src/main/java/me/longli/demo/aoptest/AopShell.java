package me.longli.demo.aoptest;

import me.longli.demo.aoptest.service.ServiceA;
import me.longli.demo.aoptest.service.ServiceB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@ShellComponent
public class AopShell {

    @Autowired
    private ServiceA serviceA;

    @Autowired
    private ServiceB serviceB;

    @ShellMethod(value = "sayA", key = "sayA")
    public String sayA() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = serviceA.sayA();
        return future.get();
    }

    @ShellMethod(value = "sayB", key = "sayB")
    public String sayB() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = serviceB.sayB();
        return future.get();
    }
}
