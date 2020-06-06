package com.ggk.juc.volatiles.singleton;

/**
 * 单例模式有6种（懒汉模式/饿汉模式）
 */
public class SingletonDemo {
    private static volatile SingletonDemo instance = null;
    private SingletonDemo(){
        System.out.println(Thread.currentThread().getName() + "\t 我是构造方法SingletonDemo()");
    }

    public static SingletonDemo getInstance(){
        if (instance == null) {
            instance = new SingletonDemo();
        }
        return instance;
    }

    //DCL模式（Double Check Lock双端检锁机制）
    //这种不一定正确（如果没有控制住指令重排就会出现异常现象，但是这个几率比较小）
    public static SingletonDemo getInstance1(){
        if (instance == null) {
            synchronized (SingletonDemo.class){
                if (instance == null){
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        //单机版
        System.out.println(SingletonDemo.getInstance1() == SingletonDemo.getInstance1());
        System.out.println(SingletonDemo.getInstance1() == SingletonDemo.getInstance1());
        System.out.println(SingletonDemo.getInstance1() == SingletonDemo.getInstance1());
        System.out.println("---------------------");
        //多线程
        for (int i = 1; i <= 10; i++){
            new Thread(SingletonDemo::getInstance1,String.valueOf(i)).start();
        }
    }
}
