package com.ggk.juc.volatiles;

import java.util.concurrent.TimeUnit;

class MyData{
    volatile int number = 0;

    public void addTo60(){
        this.number = 60;
    }

    //请注意，此时number前面是加了volatile关键字修饰的
    public void addPlusPlus(){
        number ++;
    }
}

/**
 * 1. 验证 volatile 的可见性
 *   1.1. 假如 int number = 0; number变量之前根本没有添加volatile关键字修饰,没有可见性
 *   1.2. 添加了volatile，可以解决可见性问题。
 * 2. 验证 volatile 不保证原子性
 *   2.1. 原子性指的是什么意思？
 *          不可分割，完整性，也就是某个线程正在做某个具体业务的时候，中间不可以被加塞或者被分割，需要整体完整。
 *          要么同时成功，要么同时失败。
 *   2.2. volatile不保证原子性的案例演示
 *   2.3. 为什么不能保证原子性 参加图：https://app.yinxiang.com/shard/s24/nl/27650695/6d9cbb24-4e69-4ec6-bd87-57e305b4b2cf
 */
public class volatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();

        for (int i=0; i < 20; i++){
            new Thread(()->{
                for (int j =0; j <= 1000; j++){
                    myData.addPlusPlus();
                }
            },String.valueOf(i)).start();
        }

        //需要等待上面的20个线程都计算完成后，再用main线程取得最终的结果值看是多少？
//        //暂停一会
//        try {
//            TimeUnit.SECONDS.sleep(5);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        while (Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + " \t finally number value: " + myData.number);
    }

    private static void seeOkByVolatile() {
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
