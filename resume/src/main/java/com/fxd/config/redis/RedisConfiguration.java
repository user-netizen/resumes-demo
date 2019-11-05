package com.fxd.config.redis;

import com.fxd.cache.JedisPoolWriper;
import com.fxd.cache.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 配置 redis
 */
@Configuration
public class RedisConfiguration {
    @Value("${redis.hostname}")
    private String hostname;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.pool.maxActive}")
    private int maxTotal;
    @Value("${redis.pool.maxIdle}")
    private int maxIdle;
    @Value("${redis.pool.maxWait}")
    private long maxWaitMillis;
    @Value("${redis.pool.testOnBorrow}")
    private boolean testOnBorrow;

    @Autowired
    private JedisPoolConfig jedisPoolConfig;
    @Autowired
    private JedisPoolWriper jedisPoolWriper;
    @Autowired
    private JedisUtil jedisUtil;

    /**
     * 创建redis连接池的设置
     *
     * @return
     */
    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig createJedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        //控制一个pool可分配多少个jedis实例
        jedisPoolConfig.setMaxTotal(maxTotal);
        //连接池中最多可空闲maxIdle个连接，这里取值20
        jedisPoolConfig.setMaxIdle(maxIdle);
        //最大等待时间当没有可用连接的
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        //在获取连接的时候检查有效性
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);

        return jedisPoolConfig;
    }

    /**
     * 创建redis连接池，并做相关配置
     *
     * @return
     */
    @Bean(name = "jedisPoolWriper")
    public JedisPoolWriper createJedisPoolWriper() {
        JedisPoolWriper jedisPoolWriper = new JedisPoolWriper(jedisPoolConfig,
                hostname, port);
        return jedisPoolWriper;
    }

    /**
     * 创建redis工具类，封装redis的连接以进行相关的操作
     *
     * @return
     */
    @Bean(name = "jedisUtil")
    public JedisUtil createJedisUtil() {
        JedisUtil jedisUtil = new JedisUtil();
        jedisUtil.setJedisPool(jedisPoolWriper);
        return jedisUtil;
    }

    /**
     * redis的key操作
     * @return
     */
    @Bean(name = "jedisKeys")
    public JedisUtil.Keys createJedisKeys() {
        JedisUtil.Keys jedisKeys = jedisUtil.new Keys();
        return jedisKeys;
    }

    /**
     * redis的Strings操作
     * @return
     */
    @Bean(name = "jedisStrings")
    public JedisUtil.Strings createJedisStrings() {
        JedisUtil.Strings jedisStrings = jedisUtil.new Strings();
        return jedisStrings;
    }

    /**
     * redis的Lists操作
     * @return
     */
    @Bean(name = "jedisLists")
    public JedisUtil.Lists createJedisLists() {
        JedisUtil.Lists jedisLists = jedisUtil.new Lists();
        return jedisLists;
    }

    /**
     * redis的Sets操作
     * @return
     */
    @Bean(name = "jedisSets")
    public JedisUtil.Sets createJedisSets() {
        JedisUtil.Sets jedisSets = jedisUtil.new Sets();
        return jedisSets;
    }

    /**
     * redis的Hash操作
     * @return
     */
    @Bean(name = "jedisHash")
    public JedisUtil.Hash createJedisHash() {
        JedisUtil.Hash jedisHash = jedisUtil.new Hash();
        return jedisHash;
    }
}
