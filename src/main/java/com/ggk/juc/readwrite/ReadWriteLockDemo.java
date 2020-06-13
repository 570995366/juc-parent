package com.ggk.juc.readwrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyCache { //资源类
    private volatile Map<String,Object> map = new HashMap<>();

    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    public void put(String key,Object value) throws InterruptedException {
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入:"+ key );
            TimeUnit.MILLISECONDS.sleep(300);
            map.put(key,value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成:" + key );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwLock.writeLock().unlock();
        }

    }

    public void get(String key) throws InterruptedException {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在读取：");
            TimeUnit.MILLISECONDS.sleep(300);
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取完成：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public void clear(){
        map.clear();
    }
}

/**
 * 多个线程同时读一个资源类没有问题，所以为了满足并发量，读取共享资源应该可以同时进行。
 * 但是：如果有一个线程想去写共享资源时，就不应该再有其他线程可以对该资源进行读或写。
 * 总结：
 *  读-读能共存
 *  读-写不能共存
 *  写-写不能共存
 *  写操作：原子+独占。整个过程必须是一个完整的统一体，中间不能被分割，被打断。
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i=1;i<=5;i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    myCache.put(finalI +"", finalI +"");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
        for (int i=1;i<=5;i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    myCache.get(finalI+"");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
