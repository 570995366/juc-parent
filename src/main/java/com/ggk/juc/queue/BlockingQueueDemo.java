package com.ggk.juc.queue;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 1. 队列
 * 2. 阻塞队列
 *    2.1 阻塞队列有没有好的一面
 *    2.2 不得不阻塞，如何管理
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        //List list = null;
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
//        System.out.println(blockingQueue.add("A"));
//        System.out.println(blockingQueue.add("B"));
//        System.out.println(blockingQueue.add("C"));
//
//        System.out.println(blockingQueue.element());
//
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
//        System.out.println(blockingQueue.remove());
//
//        System.out.println(blockingQueue.offer("A"));
//        System.out.println(blockingQueue.offer("A"));
//        System.out.println(blockingQueue.offer("A"));
//        System.out.println(blockingQueue.offer("X"));
//
//        System.out.println(blockingQueue.peek());
//
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        System.out.println(blockingQueue.poll());
//        blockingQueue.put("A");
//        blockingQueue.put("A");
//        blockingQueue.put("A");

        System.out.println("====================");
//        blockingQueue.put("S");
//        blockingQueue.take();
//        blockingQueue.take();
//        blockingQueue.take();
//        blockingQueue.take();
        System.out.println(blockingQueue.offer("A", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("A", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("A", 2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("A", 2, TimeUnit.SECONDS));

    }
}
