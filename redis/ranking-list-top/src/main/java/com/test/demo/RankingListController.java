package com.test.demo;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

/**
 * @author chao.cheng
 * @createTime 2020/7/13 2:29 下午
 * @description
 **/
@Controller
@Slf4j
@RequestMapping("/demo")
public class RankingListController {

    public static final String SCORE_RANK = "score_rank";

    @Autowired
    private StringRedisTemplate template;

    /**
     * 操作展示入口
     */
    @RequestMapping("/show")
    public void show() {
        batchAdd();
        top10();

    }

    public void batchAdd() {
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet();
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            //实现了TypeTuple接口，在默认的情况下Spring就会把带有分数的有序集合的值和分数封装到这个类中，这样就可通过这个类对象读取对应的值和分数了。
            DefaultTypedTuple<String> tuple = new DefaultTypedTuple<>("张三" + i, 1D + 1);
            tuples.add(tuple);
        }

        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

        template.opsForZSet().add(SCORE_RANK, tuples);
    }

    public void top10() {
        //按索引倒序排序，指定区间的
        Set<String> range = template.opsForZSet().reverseRange(SCORE_RANK, 0,10);
        log.info("获取到的排行列表："+ JSON.toJSONString(range));

        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = template.opsForZSet().reverseRangeWithScores(SCORE_RANK, 0, 10);
        log.info("获取到的分数列表："+JSON.toJSONString(rangeWithScores));

    }
}
