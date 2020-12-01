package com.bros.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author： zxj
 * @date： 2020/11/13 11:00
 */
public class MultiThreadOnlyOneSuccess {

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    latch.countDown();
                    Thread.sleep(threadId * 100);
                    System.out.println("[thread-" + threadId +"] completed.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                }
            }).start();
        }

        latch.await();
        System.out.println("[task] completed");
    }
}
