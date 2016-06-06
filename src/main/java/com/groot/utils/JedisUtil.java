package com.groot.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.util.ResourceBundle;

/**
 * Created by SubDong on 2015/1/27.
 */
public class JedisUtil {

    private static JedisPool pool;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("redis");
        if (bundle == null) {
            throw new IllegalArgumentException(
                    "[redis.properties] is not found!");
        }
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.valueOf(bundle
                .getString("redis.pool.maxActive")));
        config.setMaxIdle(Integer.valueOf(bundle
                .getString("redis.pool.maxIdle")));
        config.setTestOnBorrow(Boolean.valueOf(bundle
                .getString("redis.pool.testOnBorrow")));
        config.setTestOnReturn(Boolean.valueOf(bundle
                .getString("redis.pool.testOnReturn")));
        pool = new JedisPool(config, bundle.getString("redis.ip"),
                Integer.valueOf(bundle.getString("redis.port")), Protocol.DEFAULT_TIMEOUT,
                bundle.getString("redis.password"));
    }

    public static Jedis get() {
        return pool.getResource();
    }

    public static void returnJedis(Jedis jedis) {
        pool.returnResource(jedis);
    }

    public static void returnBrokenJedis(Jedis jedis) {
        pool.returnBrokenResource(jedis);
    }

    public static void main(String args[]) {

    }
}
