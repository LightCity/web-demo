package me.longli.demo.aoptest.service;

import me.longli.demo.aoptest.aop.MyAnnotation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ServiceAImpl implements ServiceA, InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    @Lazy
    @Autowired
    private ServiceA serviceA;

    @Override
    public void afterPropertiesSet() throws Exception {
        //serviceA = applicationContext.getBean(ServiceA.class);
    }

    @MyAnnotation(someInfo = "ServiceAImpl_sayA") // TODO 由于存在循环依赖，结合@Async注解，使得其他地方通过@Autowired注入的ServiceA是一个不完整的代理，这里的自定义注解会失效
    @Async
    @Override
    public CompletableFuture<String> sayA() {
        return CompletableFuture.completedFuture("ServiceAImpl");
    }
}
