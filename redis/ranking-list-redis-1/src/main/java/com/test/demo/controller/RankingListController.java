package com.test.demo.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * @author chao.cheng
 * @createTime 2020/6/8 8:29 上午
 * @description
 **/
@RestController
@RequestMapping("/demo")
@Slf4j
public class RankingListController {

    public static final String SCORE_RANK = "score_rank";

    @Autowired
    private StringRedisTemplate template;

    @RequestMapping("/show")
    public void show() {
        batchAdd();
        top10();
        add();
        find();

    }

    public void batchAdd() {
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            DefaultTypedTuple<String> tuple = new DefaultTypedTuple<>("张三" + i, 1D + i);
            tuples.add(tuple);
        }
        log.info("循环时间:" + (System.currentTimeMillis() - start));
        Long num = template.opsForZSet().add(SCORE_RANK, tuples);
        log.info("批量新增时间:" +(System.currentTimeMillis() - start));
        log.info("受影响行数：" + num);
    }

    public void top10() {

        Set<String> range = template.opsForZSet().reverseRange(SCORE_RANK, 0, 10);
        log.info("获取到的排行列表:" + JSON.toJSONString(range));
        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = template.opsForZSet().reverseRangeWithScores(SCORE_RANK, 0, 10);
        log.info("获取到的排行和分数列表:" + JSON.toJSONString(rangeWithScores));
    }


    public void add() {
        template.opsForZSet().add(SCORE_RANK, "李四", 9000);
    }


    public void find(){
        Long rankNum = template.opsForZSet().reverseRank(SCORE_RANK, "李四");
        log.info("李四的个人排名：" + rankNum);

        Double score = template.opsForZSet().score(SCORE_RANK, "李四");
        log.info("李四的分数:" + score);
    }

    public void count(){
        Long count = template.opsForZSet().count(SCORE_RANK, 8001, 9000);
        System.out.println("统计8001-9000之间的人数:" + count);
    }


}
