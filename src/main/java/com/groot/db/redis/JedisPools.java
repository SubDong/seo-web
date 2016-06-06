package com.groot.db.redis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * Created by yousheng on 14/11/13.
 */
public class JedisPools {


    private static JedisPool jedisPool;

    private static boolean init = false;

    public static void init(String host) {
        if (init) {
            return;
        }

        jedisPool = new JedisPool(new JedisPoolConfig(), host, Protocol.DEFAULT_PORT,Protocol.DEFAULT_TIMEOUT,"3edcvfr4");

        if (jedisPool != null) {
            init = true;
        }
    }

    public static JedisPool getPool() {
        return jedisPool;
    }
}
