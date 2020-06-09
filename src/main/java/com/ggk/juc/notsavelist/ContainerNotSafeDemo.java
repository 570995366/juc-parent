package com.ggk.juc.notsavelist;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 集合类不安全的问题
 */
public class ContainerNotSafeDemo {
    public static void main(String[] args) {
        //arrayListNoSafe();
        //setNoSafe();
        mapNoSafe();
        //java.util.ConcurrentModificationException

        /**
         * 1. 故障现象
         *      java.util.ConcurrentModificationException
         * 2. 导致原因
         *      并发修改争抢导致
         * 3. 解决方案
         *      3.1 new Vector<>()  线程安全的
         *      3.2 Collections.synchronizedList(new ArrayList<>());  加锁
         *      3.3 new CopyOnWriteArrayList<>()    写时复制，（首先加锁，保证只有一个线程操作，然后扩容集合容量+1，把要写入的对象放在集合的尾端，放入集合中，释放锁）
         *          参考 add方法
         *          public boolean add(E e) {
         *              获得锁
         *              final ReentrantLock lock = this.lock;
         *              加锁
         *              lock.lock();
         *              try {
         *                  得到当前集合
         *                  Object[] elements = getArray();
         *                  获取集合长度
         *                  int len = elements.length;
         *                  扩容加1
         *                  Object[] newElements = Arrays.copyOf(elements, len + 1);
         *                  当前对象在集合的尾端赋值
         *                  newElements[len] = e;
         *                  放入到集合中
         *                  setArray(newElements);
         *                  return true;
         *              } finally {
         *                  释放锁
         *                  lock.unlock();
         *              }
         *          }
         * 4. 优化建议
         */

        /**
         * 写时复制
         * CopyOnWrite容器即写时复制的容器。往一个容器中添加元素的时候，不直接往当前容器Object[]中添加，而是先将当前容器Object[]进行Copy,
         * 复制出一个新的容器Object[] newElements,然后新的容器Object[] newElements里添加元素，添加完元素之后，
         * 再将原容器的引用指向新的容器，setArray(newElements);这样做的好处是可以对CopyOnWrite容器进行并发的读，
         * 而不需要加锁，因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。
         *     public boolean add(E e) {
         *         final ReentrantLock lock = this.lock;
         *         lock.lock();
         *         try {
         *             Object[] elements = getArray();
         *             int len = elements.length;
         *             Object[] newElements = Arrays.copyOf(elements, len + 1);
         *             newElements[len] = e;
         *             setArray(newElements);
         *             return true;
         *         } finally {
         *             lock.unlock();
         *         }
         *     }
         */
    }

    public static void arrayListNoSafe(){
        //List<String> list = new ArrayList<>();
        List<String> list = new CopyOnWriteArrayList<>();
        //list.stream().forEach(System.out::println);
//        List<String> list = Arrays.asList("a","b","c");
//        list.forEach(System.out::println);
        for (int i= 1; i<=3;i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }

    public static void setNoSafe(){
        //Set<String> set = new HashSet<>();
        //Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i= 1; i<=3;i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
            },String.valueOf(i)).start();
        }
        // HashSet底层是HashMap key是当前对象 value是PRESENT恒定值
    }

    public static void mapNoSafe(){
        //Map<String,String> map = new HashMap<>();
        Map<String,String> map = new ConcurrentHashMap<>();
        for (int i= 1; i<=3;i++) {
            new Thread(()->{
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0,8));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }
}
