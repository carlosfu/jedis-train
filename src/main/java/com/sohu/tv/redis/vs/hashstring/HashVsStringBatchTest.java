package com.sohu.tv.redis.vs.hashstring;

import java.util.ArrayList;
import java.util.List;

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
public class HashVsStringBatchTest extends BaseTest {

    private final static int BATCH_SIZE = 500;

    @Test
    public void test() {
        Jedis jedis = RedisStandAloneUtil.getJedisPool().getResource();
        jedis.flushAll();

        List<VideoInfo> videoInfoList = new ArrayList<VideoInfo>();
        for (int i = 0; i < TOTAL_SIZE; i++) {
            VideoInfo videoInfo = getVideoInfo(i);
            videoInfoList.add(videoInfo);
            if (i % BATCH_SIZE == 0 || i == TOTAL_SIZE - 1) {
                logger.info("execute {}", i);
//                stringV1.batchSave(videoInfoList);
//                 stringV2.batchSave(videoInfoList);
                 hash.batchSave(videoInfoList);
                videoInfoList = new ArrayList<VideoInfo>();
            }
        }

        logger.info(jedis.info("memory"));
        jedis.close();

    }

}
