package com.ggk.juc.callable;

import java.util.concurrent.*;

class MyThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() +"come in Callable -----");
        TimeUnit.SECONDS.sleep(3);
        return 1024;
    }
}
/**
 * 多线程中第3种获得多线程的方式
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //两个线程一个main一个AA futureTask
        FutureTask<Integer> futureTask = new FutureTask<>(new MyThread());
//        FutureTask<Integer> futureTask2 = new FutureTask<>(new MyThread());
//        FutureTask<Integer> futureTask3 = new FutureTask<>(new MyThread());
        new Thread(futureTask,"AA").start();
        new Thread(futureTask,"BB").start();
//        new Thread(futureTask3,"CC").start();
        //int result02 = futureTask.get();
        System.out.println(Thread.currentThread().getName()+"**************");

        int result01 = 100;

        while (!futureTask.isDone()) {

        }
        int result02 = futureTask.get();    //要求获得Callable的计算结果，如果没有计算完成就要求结果，或导致阻塞，直到计算完成。
        System.out.println("---- result :" + (result01 + result02));
    }
}
