package com.sohu.tv.redis.sentinel;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

/**
 * @author leifu
 * @Date 2016-1-31
 * @Time 下午8:16:26
 */
public class SentinelTest {
    
    private Logger logger = LoggerFactory.getLogger(SentinelTest.class);
    
    private static JedisSentinelPool jedisSentinelPool;

    @BeforeClass
    public static void setUp() {
        // 1.1. masterName
        String masterName = "mymaster";

        // 1.2. sentinel地址集合
        Set<String> sentinels = new HashSet<String>();
        sentinels.add("10.10.53.159:26379");
        sentinels.add("10.10.53.159:26380");
        sentinels.add("10.10.53.159:26381");

        // 1.3. common-pool配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL * 5);
        poolConfig.setMaxIdle(GenericObjectPoolConfig.DEFAULT_MAX_IDLE * 3);
        poolConfig.setMinIdle(GenericObjectPoolConfig.DEFAULT_MIN_IDLE * 2);
        poolConfig.setJmxEnabled(true);
        poolConfig.setMaxWaitMillis(3000);

        // 2.初始化sentinelPool
        jedisSentinelPool = new JedisSentinelPool(masterName, sentinels, poolConfig);
    }
    

    @Test
    public void testWrite() {
        for (int i = 1; i <= 1000; i++) {
            String userIdKey = "user" + i;
            Jedis jedis = null;
            try {
                jedis = jedisSentinelPool.getResource();
                jedis.set(userIdKey, String.valueOf(i));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

    @Test
    public void testRead() {
        while (true) {
            String userIdKey = "user" + (new Random().nextInt(1000) + 1);
            Jedis jedis = null;
            try {
                jedis = jedisSentinelPool.getResource();
                logger.info("key: {}, value: {}", userIdKey, jedis.get(userIdKey));
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

}
