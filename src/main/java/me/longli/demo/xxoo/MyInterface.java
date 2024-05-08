package me.longli.demo.xxoo;

public interface MyInterface {

    default void foo() {
        System.out.println("MyInterface");
    }
}
