package me.longli.demo.generic;

import java.util.List;

@SuppressWarnings("all")
public class JavaSuperExtendKeyword {

    static class Fruit {};

    static class Apple extends Fruit {};

    static class QingdaoApple extends Apple {};

    static List<? extends Fruit> aFruitList; // aFruitList 只能get；不能add

    static List<? super   Apple> bFruitList; // bFruitList 只能add，不能get
}
