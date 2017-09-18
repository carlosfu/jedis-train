package com.sohu.tv.bean;

import java.io.Serializable;

/**
 * 视频信息
 * @author leifu
 * @Date 2016-2-4
 * @Time 下午4:34:20
 */
public class VideoInfo implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2572863814339328813L;

    /**
     * 视频id
     */
    private long id;
    
    /**
     * 用户名
     */
    private String name;
    
    /**
     * 时长
     */
    private long timelength;
    
    /**
     * 描述
     */
    private String desc;

    private String info;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimelength() {
        return timelength;
    }

    public void setTimelength(long timelength) {
        this.timelength = timelength;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "VideoInfo [id=" + id + ", name=" + name + ", timelength=" + timelength + ", desc=" + desc + "]";
    }
    
    
    
}
