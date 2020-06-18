package com.ggk.juc.prodconsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程按顺序执行 实现A->B->C三个线程启动，要求如下
 * A打印5次，B打印10次，C打印15次
 * 紧接着
 * A打印5次，B打印10次，C打印15次
 * ...
 * 来10轮
 */

class ShardDataSync{
    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print5(){
        lock.lock();
        try {
            //判断
            while (number != 1) {
                c1.await();
            }
            //干活
            for (int i =1; i<=5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t "+ i);
            }
            //通知
            number = 2;
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10(){
        lock.lock();
        try {
            //判断
            while (number != 2) {
                c2.await();
            }
            //干活
            for (int i =1; i<=10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t "+ i);
            }
            //通知
            number = 3;
            c3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print15(){
        lock.lock();
        try {
            //判断
            while (number != 3) {
                c3.await();
            }
            //干活
            for (int i =1; i<=15; i++) {
                System.out.println(Thread.currentThread().getName() + "\t "+ i);
            }
            //通知
            number = 1;
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
public class SyncAndReentrantLockDemo {
    public static void main(String[] args) {
        ShardDataSync shardDataSync = new ShardDataSync();
        new Thread(()->{
            for (int i=1;i<=10;i++) {
                shardDataSync.print5();
            }
        },"A").start();
        new Thread(()->{
            for (int i=1;i<=10;i++) {
                shardDataSync.print10();
            }
        },"B").start();
        new Thread(()->{
            for (int i=1;i<=10;i++) {
                shardDataSync.print15();
            }
        },"C").start();
    }
}
