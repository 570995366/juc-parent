package com.ggk.juc.volatiles;

import java.util.concurrent.TimeUnit;

class MyData{
    volatile int number = 0;

    public void addTo60(){
        this.number = 60;
    }
}

/**
 * 1. 验证 volatile 的可见性
 *   1.1. 假如 int number = 0; number变量之前根本没有添加volatile关键字修饰,没有可见性
 * 2.
 */
public class volatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();   //线程要操作资源类
        //第一个线程
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t come in");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + "\t updated value:"+myData.number);
        },"AAA").start();
        //第二个线程
        while (myData.number ==0){
            //main线程一直在等待循环，直到number值不再等于0
        }
        System.out.println(Thread.currentThread().getName() + "\t mission is over , main get number value :" + myData.number);
    }
}
