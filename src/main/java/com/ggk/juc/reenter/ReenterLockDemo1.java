package com.ggk.juc.reenter;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Phone1 implements Runnable{
    synchronized void sendSMS() throws Exception{
        System.out.println(Thread.currentThread().getName() + "\t invoked sendSMS()");
        sendEmail();
    }
    private synchronized void sendEmail() throws Exception{
        System.out.println(Thread.currentThread().getName() + "\t ######## invoked sendEmail()");
    }
    //---------------------------------------------------

    //ReentrantLock典型的可重入锁
    private Lock lock = new ReentrantLock();
    @Override
    public void run() {
        get();
    }

    private void get(){
        lock.lock();
        //lock.lock();
        try {
            //线程可以在任何一个它已经拥有的锁所同步着的代码块
            System.out.println(Thread.currentThread().getName() + "\t invoked get()");
            set();
        } finally {
            lock.unlock();
            //lock.unlock();
        }
    }

    private void set(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t ######## invoked set()");
        } finally {
            lock.unlock();
        }
    }
}

/**
 * 可重入锁（递归锁）
 * 指的是同一线程外层函数获得锁之后，内层递归函数任然能够获得该锁的代码
 * 在同一个线程在最外层方法获得锁时，在进入内层方法会自动获得锁
 * 也就是说，线程可以在任何一个它已经拥有的锁所同步着的代码块。
 *  case one Synchronized是典型的可重入锁
 * T1	 invoked sendSMS()              T1线程外层函数获得锁之后
 * T1	 ######## invoked sendEmail()   T1在进入内层方法会自动获得锁
 * T2	 invoked sendSMS()
 * T2	 ######## invoked sendEmail()
 *
 *  case two ReentrantLock是典型的可重入锁
 */
public class ReenterLockDemo1 {
    public static void main(String[] args) {
        Phone1 phone = new Phone1();
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

        System.out.println();

        Thread t3 = new Thread(phone,"T3");
        Thread t4 = new Thread(phone,"T4");
        t3.start();
        t4.start();
    }
}
