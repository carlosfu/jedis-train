package com.sohu.tv.redis.vs.hashstring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import com.sohu.tv.bean.VideoInfo;
import com.sohu.tv.redis.util.RedisStandAloneUtil;

/**
 * 用hash来存储视频信息
 * 
 * @author leifu
 * @Date 2016-2-4
 * @Time 下午4:38:35
 */
public class HashVideoInfoServiceImpl extends VideoInfoService {

    public void save(VideoInfo videoInfo) {
        Jedis jedis = null;
        try {
            jedis = RedisStandAloneUtil.getJedisPool().getResource();
            String key = "h:vid:" + videoInfo.getId();
            Map<String, String> videoInfoHashMap = transferVideoInfoToHashMap(videoInfo);
            jedis.hmset(key, videoInfoHashMap);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    private Map<String, String> transferVideoInfoToHashMap(VideoInfo videoInfo) {
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("id", String.valueOf(videoInfo.getId()));
        resultMap.put("name", String.valueOf(videoInfo.getName()));
        resultMap.put("timelength", String.valueOf(videoInfo.getTimelength()));
        resultMap.put("desc", String.valueOf(videoInfo.getDesc()));
        return resultMap;
    }

    @Override
    public void batchSave(List<VideoInfo> videoInfoList) {
        Jedis jedis = null;
        try {
            jedis = RedisStandAloneUtil.getJedisPool().getResource();
            Pipeline pipeline = jedis.pipelined();
            for (VideoInfo videoInfo : videoInfoList) {
                String key = "h:vid:" + videoInfo.getId();
                Map<String, String> videoInfoHashMap = transferVideoInfoToHashMap(videoInfo);
                pipeline.hmset(key, videoInfoHashMap);
            }
            pipeline.sync();
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
            jedis.hset("h:vid:" + vid, "name", newTvName);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}
