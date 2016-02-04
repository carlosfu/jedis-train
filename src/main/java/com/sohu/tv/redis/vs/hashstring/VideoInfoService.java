package com.sohu.tv.redis.vs.hashstring;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sohu.tv.bean.VideoInfo;

/**
 * 视频信息服务
 * 
 * @author leifu
 * @Date 2016-2-4
 * @Time 下午4:37:52
 */
public abstract class VideoInfoService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 保存视频信息
     * 
     * @param videoInfo
     */
    public abstract void save(VideoInfo videoInfo);
    
    /**
     * 批量保存视频信息
     * @param videoInfoList
     */
    public abstract void batchSave(List<VideoInfo> videoInfoList);

    /**
     * 模拟更新一个属性
     * @param vid
     * @param newTvName
     */
    public abstract void updateName(long vid, String newTvName);
}
