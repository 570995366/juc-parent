package com.ggk.jvm.gc;

import java.util.concurrent.TimeUnit;

public class HelloGC {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello GC");
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

}
