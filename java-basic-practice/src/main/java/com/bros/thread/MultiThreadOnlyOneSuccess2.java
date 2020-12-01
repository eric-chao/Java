package com.bros.thread;

import java.util.concurrent.*;

/**
 * @author： zxj
 * @date： 2020/11/13 11:00
 */
public class MultiThreadOnlyOneSuccess2 {

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(5);

    private static final Integer THREAD_NUM = 10;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println(getRuntimeMilis());
        EXECUTOR.shutdown();
    }

    public static Long getRuntimeMilis() throws InterruptedException, ExecutionException {
        Long startTime = System.currentTimeMillis();
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        for (int i = 1; i <= THREAD_NUM; i++) {
            final int threadId = i;
            EXECUTOR.submit(() -> {
                long s1 = System.currentTimeMillis();
                try {
                    Thread.sleep((THREAD_NUM - threadId) * 100);
                    System.out.println("[thread-" + threadId + "] completed.");
                    String result = threadId + " : " + (System.currentTimeMillis() - s1);
                    queue.offer(result, 10, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        }

        // print
        String r = queue.take();
        System.out.println("[task] " + r);

        return System.currentTimeMillis() - startTime;
    }
}
