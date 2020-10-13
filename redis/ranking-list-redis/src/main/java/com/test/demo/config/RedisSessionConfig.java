package com.test.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author chao.cheng
 * @createTime 2020/6/18 10:55 上午
 * @description
 **/
@Configuration
@EnableRedisHttpSession
public class RedisSessionConfig {

}
