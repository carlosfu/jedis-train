package com.sohu.tv.redis.util;

import com.sohu.tv.bean.VideoInfo;
import com.sohu.tv.redis.serializable.ProtostuffSerializer;

/**
 * 序列化工具
 * 
 * @author leifu
 * @Date 2016-2-4
 * @Time 下午4:48:44
 */
public class SerializeUtil {

    /**
     * google protobuf 序列化工具java版本
     */
    private static ProtostuffSerializer protostuffSerializer = new ProtostuffSerializer();

    public static byte[] serialize(VideoInfo videoInfo) {
        return protostuffSerializer.serialize(videoInfo);
    }

    public static VideoInfo deserialize(byte[] bytes) {
        return protostuffSerializer.deserialize(bytes);
    }
}
