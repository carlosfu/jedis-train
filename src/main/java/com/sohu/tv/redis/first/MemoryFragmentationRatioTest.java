package com.sohu.tv.redis.first;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * 内存碎片率
 * @author leifu
 * @Date 2016年3月21日
 * @Time 上午11:05:22
 */
public class MemoryFragmentationRatioTest {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(new File("all.txt")));

        try {
            String line = null;
            Set<String> set = new HashSet<String>();
            while ((line = br.readLine()) != null) {
                String[] items = line.split("\t");
                String appId = items[0];
                String host = items[1];
                int port = Integer.parseInt(items[2]);
                if (set.add(appId)) {
                    System.out.println("=======appId:" + appId + "=======");
                }
                Jedis jedis = null;
                try {
                    jedis = new Jedis(host, port);
                    String[] memorys = jedis.info("memory").split("\n");
                    String[] replications = jedis.info("replication").split("\n");

                    for (String replication : replications) {
                        if (replication.contains("role:")) {
                            System.out.print(replication.split(":")[1].trim() + "\t");
                        }
                    }
                    for (String memory : memorys) {
                        if (memory.contains("mem_fragmentation_ratio")) {
                            System.out.print(host + ":" + port + "\t" + memory.split(":")[1]);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (jedis != null) {
                        jedis.close();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
        }

    }
}
