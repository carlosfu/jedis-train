package com.sohu.tv.redis.vs.hashstring;

import java.util.List;

import redis.clients.jedis.Jedis;

import com.sohu.tv.bean.VideoInfo;
import com.sohu.tv.redis.util.RedisStandAloneUtil;
import com.sohu.tv.redis.util.SerializeUtil;

/**
 * 用字符串序列化方式实现
 * 
 * @author leifu
 * @Date 2016-2-4
 * @Time 下午4:38:35
 */
public class StringAndSerializeVideoInfoServiceImpl extends VideoInfoService {

    public void save(VideoInfo videoInfo) {
        Jedis jedis = null;
        try {
            jedis = RedisStandAloneUtil.getJedisPool().getResource();
            String key = "s:vid:" + videoInfo.getId();
            byte[] value = SerializeUtil.serialize(videoInfo);
            jedis.set(key.getBytes(), value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public void batchSave(List<VideoInfo> videoInfoList) {
        Jedis jedis = null;
        try {
            jedis = RedisStandAloneUtil.getJedisPool().getResource();
            byte[][] kvs = new byte[videoInfoList.size() * 2][];
            int i = 0;
            for (VideoInfo videoInfo : videoInfoList) {
                String vid = String.valueOf(videoInfo.getId());
                String key = "s:vid:" + vid;
                kvs[i++] = key.getBytes();
                kvs[i++] = SerializeUtil.serialize(videoInfo);
            }
            jedis.mset(kvs);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }        
    }

    @Override
    public void updateName(long vid, String newTvName) {
        Jedis jedis = null;
        try {
            jedis = RedisStandAloneUtil.getJedisPool().getResource();
            String key = "s:vid:" + vid;
            VideoInfo videoInfo = SerializeUtil.deserialize(jedis.get(key.getBytes()));
            videoInfo.setName(newTvName);
            byte[] value = SerializeUtil.serialize(videoInfo);
            jedis.set(key.getBytes(), value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}