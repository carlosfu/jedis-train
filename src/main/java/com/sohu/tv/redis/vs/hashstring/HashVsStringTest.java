package com.sohu.tv.redis.vs.hashstring;

import org.junit.Test;

import redis.clients.jedis.Jedis;

import com.sohu.tv.bean.VideoInfo;
import com.sohu.tv.redis.util.RedisStandAloneUtil;

/**
 * 哈希和字符串串行对比测试
 * 
 * @author leifu
 * @Date 2016-2-4
 * @Time 下午5:01:35
 */
public class HashVsStringTest extends BaseTest {

    @Test
    public void test() {
        Jedis jedis = RedisStandAloneUtil.getJedisPool().getResource();
        jedis.flushAll();
        for (int i = 0; i < TOTAL_SIZE; i++) {
            if (i % 100 == 0) {
                logger.info("execute {}", i);
            }
            VideoInfo videoInfo = getVideoInfo(i);
            stringV1.save(videoInfo);
        }

        logger.info(jedis.info("memory"));
        jedis.close();

    }

}
