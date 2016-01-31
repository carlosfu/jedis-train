package com.sohu.tv.redis.pipeline;

import java.util.List;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * pipeline简单性能测试
 * 
 * @author leifu
 * @Date 2016-1-27
 * @Time 上午8:24:20
 */
public class PipelineTest {

    private final static int SIZE = 20;

    @Test
    public void testNoPipeline() {
        long startTime = System.currentTimeMillis();
        Jedis jedis = null;
        try {
            jedis = new Jedis("10.10.53.159", 6380);
            for (int i = 0; i < SIZE; i++) {
                if (i % 100 == 0) {
                    System.out.println(i);
                }
                jedis.hset("hash" + i, "field" + i, "value" + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        System.out.println("testNoPipeline " + SIZE + " hset, cost time: " + (System.currentTimeMillis() - startTime)
                + "ms");
    }

    @Test
    public void testPipeline() {
        long startTime = System.currentTimeMillis();
        Jedis jedis = null;
        try {
            jedis = new Jedis("10.10.53.159", 6380);
            int batchSize = 2;
            int times = SIZE / batchSize;
            for (int i = 0; i < times; i++) {
                Pipeline pipeline = jedis.pipelined();
                for (int j = i * batchSize; j < (i + 1) * batchSize; j++) {
                    pipeline.hset("hash" + j, "field" + j, "value" + j);
                }
                pipeline.set("hello", "world");
                List<Object> resultObjectList = pipeline.syncAndReturnAll();
                for (Object object : resultObjectList) {
                    System.out.println(object.toString());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        System.out.println("testPipeline " + SIZE + " hset, cost time: " + (System.currentTimeMillis() - startTime)
                + "ms");
    }

}
