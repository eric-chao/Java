package com.test.demo.controller;

import com.test.demo.lock.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author chao.cheng
 * @createTime 2020/6/8 3:49 下午
 * @description
 **/
@Controller
@Slf4j
public class LockController {

    @Autowired
    private RedisLock redisLock;

    int count = 0;

    @RequestMapping("/index")
    @ResponseBody
    public String index() throws InterruptedException {

        int clientcount = 10;

        CountDownLatch countDownLatch = new CountDownLatch(clientcount);

        ExecutorService executorService = Executors.newFixedThreadPool(clientcount);
        long start = System.currentTimeMillis();
        for (int i = 0; i < clientcount; i++) {
            executorService.execute(() -> {

                String id = UUID.randomUUID().toString().replace("-", "").toLowerCase();
                try {
                    redisLock.lock(id);
                    count++;
                } finally {
                    redisLock.unlock(id);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        long end = System.currentTimeMillis();
        log.info("执行线程数:{},总耗时:{},count数为:{}", clientcount, end - start, count);
        return "Hello";
    }
}
