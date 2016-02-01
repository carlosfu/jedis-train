package com.sohu.tv.redis.cluster;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * jedisCluster测试
 * @author leifu
 * @Date 2016年2月1日
 * @Time 下午5:18:14
 */
public class RedisClusterTest {

    private Logger logger = LoggerFactory.getLogger(RedisClusterTest.class);

    private static JedisCluster jedisCluster;

    @BeforeClass
    public static void setUp() {
        // 1. 所有节点
        Set<HostAndPort> nodeList = new HashSet<HostAndPort>();
        nodeList.add(new HostAndPort("10.10.53.159", 8000));
        nodeList.add(new HostAndPort("10.10.53.159", 8001));
        nodeList.add(new HostAndPort("10.10.53.159", 8002));
        nodeList.add(new HostAndPort("10.10.53.159", 8003));
        nodeList.add(new HostAndPort("10.10.53.159", 8004));
        nodeList.add(new HostAndPort("10.10.53.159", 8005));

        // 2. poolConfig
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();

        // 3. timeout
        int timeout = 1000;

        jedisCluster = new JedisCluster(nodeList, timeout, poolConfig);
    }

    @Test
    public void testWrite() {
        for (int i = 1; i <= 1000; i++) {
            String userIdKey = "user" + i;
            jedisCluster.set(userIdKey, String.valueOf(i));
        }
    }

    @Test
    public void testRead() {
        while (true) {
            String userIdKey = "user" + (new Random().nextInt(1000) + 1);
            logger.info("key: {}, value: {}", userIdKey, jedisCluster.get(userIdKey));
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

}
