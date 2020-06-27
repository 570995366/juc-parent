package com.ggk.jvm.oom;

public class StackOverflowErrorDemo {
    public static void main(String[] args) {
        stackOverflowError();
    }
    private static void stackOverflowError(){
        stackOverflowError();//Exception in thread "main" java.lang.StackOverflowError
    }
}
