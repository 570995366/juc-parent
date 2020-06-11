package com.ggk.juc.reenter;

class Phone {
    public synchronized void sendSMS() throws Exception{
        System.out.println(Thread.currentThread().getName() + "\t invoked sendSMS()");
        sendEmail();
    }
    public synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getName() + "\t ######## invoked sendEmail()");
    }
}

/**
 * 可重入锁（递归锁）
 * 指的是同一线程外层函数获得锁之后，内层递归函数任然能够获得该锁的代码
 * 在同一个线程在最外层方法获得锁时，在进入内层方法会自动获得锁
 * 也就是说，线程可以在任何一个它已经拥有的锁所同步着的代码块。
 *
 * T1	 invoked sendSMS()              T1线程外层函数获得锁之后
 * T1	 ######## invoked sendEmail()   T1在进入内层方法会自动获得锁
 * T2	 invoked sendSMS()
 * T2	 ######## invoked sendEmail()
 */
public class ReenterLockDemo {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"T1").start();

        new Thread(()->{
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"T2").start();
    }
}
