package com.ggk.juc.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. CAS是什么？
 *      比较交换  compare And Set
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(atomicInteger.compareAndSet(5, 2019)+" \t current value: " + atomicInteger.get());

        System.out.println(atomicInteger.compareAndSet(5, 1024)+" \t current value: " + atomicInteger.get());
    }
}
