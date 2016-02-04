package com.sohu.tv.redis.vs.hashstring;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sohu.tv.bean.VideoInfo;

/**
 * 
 * @author leifu
 * @Date 2016-2-4
 * @Time 下午10:58:41
 */
public class BaseTest {
    
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    
    protected VideoInfoService stringV1 = new StringAndSerializeVideoInfoServiceImpl();

    protected VideoInfoService stringV2 = new StringV2VideoInfoServiceImpl();

    protected VideoInfoService hash = new HashVideoInfoServiceImpl();
    
    protected final static int TOTAL_SIZE = 10000;

    protected VideoInfo getVideoInfo(int index) {
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setId(index);
        videoInfo.setName("电视剧:" + index);
        videoInfo.setTimelength(3600);
        videoInfo.setDesc("电视剧描述" + UUID.randomUUID().toString().substring(0, 16));
        return videoInfo;
    }
}
