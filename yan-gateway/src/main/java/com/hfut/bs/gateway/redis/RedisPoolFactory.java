package com.hfut.bs.gateway.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by chenxiong on 18/12/2.
 */
@Service("redisPoolFactory")
public class RedisPoolFactory {

    private Logger logger = LoggerFactory.getLogger(RedisPoolFactory.class);

    /*
     * @Bean注解作用在方法上就是产生一个bean交给spring容器管理
     * */
    @Bean
    public JedisPool jedisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(500);
        jedisPoolConfig.setMaxWaitMillis(500);
        jedisPoolConfig.setMaxTotal(1000);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 1000, null);
        logger.info("JedisPool注入成功！");
        logger.info("redis地址：" + "127.0.0.1" + ":" + "6379");
        return jedisPool;
    }

}
