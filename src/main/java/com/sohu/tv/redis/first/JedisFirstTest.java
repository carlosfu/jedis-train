package com.sohu.tv.redis.first;
import redis.clients.jedis.Jedis;

/**
 * jedis第一次编写学习
 * 
 * @author leifu
 * @Date 2015-11-17
 * @Time 下午3:03:12
 */
public class JedisFirstTest {
    public static void main(String[] args) {
        Jedis jedis = null;
        try {
            jedis = new Jedis("10.10.53.159", 6389);
            String pingResult = jedis.ping();
            System.out.println("pingResult: " + pingResult);

            String key = "hello";
            // 设置redis k-v
            String setResult = jedis.set(key, "world");
            System.out.println("setResult: " + setResult);

            // 获取key值
            String getResult = jedis.get(key);
            System.out.println("getResult: " + getResult);

            // 删除k-v
            Long delResult = jedis.del(key);
            System.out.println("delResult: " + delResult);

            // 删除后重新获取key值
            getResult = jedis.get(key);
            System.out.println("after del, getResult: " + getResult);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }

    }
}
