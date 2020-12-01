package com.bros.annotation;

import java.lang.reflect.Field;

public class AnnotationTest {


    public static void main(String[] args) throws NoSuchFieldException {

        Apple apple = new Apple();

        System.out.println(apple.getName());
        System.out.println(apple.getColor());

        Class<Apple> clazz = Apple.class;
        Field field = clazz.getDeclaredField("name");
        field.setAccessible(true);
        FruitName name = field.getAnnotation(FruitName.class);

        System.out.println(name.value());

    }
}
