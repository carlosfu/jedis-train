package com.sohu.tv.redis.vs.hashstring;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;

import com.sohu.tv.bean.VideoInfo;
import com.sohu.tv.redis.util.RedisStandAloneUtil;

/**
 * 每个属性值作为key-value
 * 
 * @author leifu
 * @Date 2016-2-4
 * @Time 下午4:52:10
 */
public class StringV2VideoInfoServiceImpl extends VideoInfoService {

    public void save(VideoInfo videoInfo) {
        Jedis jedis = null;
        try {
            jedis = RedisStandAloneUtil.getJedisPool().getResource();
            String vid = String.valueOf(videoInfo.getId());
            jedis.set("vid:" + vid + ":id", vid);
            jedis.set("vid:" + vid + ":name", videoInfo.getName());
            jedis.set("vid:" + vid + ":timelength", String.valueOf(videoInfo.getTimelength()));
            jedis.set("vid:" + vid + ":desc", videoInfo.getDesc());
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
            List<String> kvs = new ArrayList<String>();
            for (VideoInfo videoInfo : videoInfoList) {
                String vid = String.valueOf(videoInfo.getId());
                kvs.add("vid:" + vid + ":id");
                kvs.add(vid);
                kvs.add("vid:" + vid + ":name");
                kvs.add(videoInfo.getName());
                kvs.add("vid:" + vid + ":timelength");
                kvs.add(String.valueOf(videoInfo.getTimelength()));
                kvs.add("vid:" + vid + ":desc");
                kvs.add(videoInfo.getDesc());
            }
            jedis.mset(kvs.toArray(new String[kvs.size()]));
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
            jedis.set("vid:" + vid + ":name", newTvName);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}