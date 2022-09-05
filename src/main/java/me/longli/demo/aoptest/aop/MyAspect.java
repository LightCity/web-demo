package me.longli.demo.aoptest.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Order(Integer.MIN_VALUE)
@Component
@Aspect
public class MyAspect {

    @Around(value = "@annotation(me.longli.demo.aoptest.aop.MyAnnotation)")
    public Object logInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
        String someInfo = annotation.someInfo();
        System.out.println("=== before invoke " + method.getName() + " === " + someInfo);
        Object result = null;
        try {
            result = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            System.out.println("=== during invoke " + method.getName() + " occur exception" + e.getMessage() + " === " + someInfo);
            throw e;
        } finally {
            System.out.println("=== end invoke " + method.getName() + " === " + someInfo);
        }
        return result;
    }
}
