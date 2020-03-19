package com.dream.content.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

//@Component
//单机版redis
public class JedisClientPool implements JedisClient{
    @Autowired
    private JedisPool jedisPool;
    @Override
    public String set(String key, String value) {
        //1.先得到jedis
        Jedis resource = jedisPool.getResource();
        //2.执行操作
        String set = resource.set(key,value);
        resource.close();
        return set;
    }

    @Override
    public String get(String key) {
        //1.先得到jedis
        Jedis resource = jedisPool.getResource();
        //2.执行操作
        String get = resource.get(key);
        resource.close();
        return get;
    }

    @Override
    public Boolean exists(String key) {
        //.先得到jedis
        Jedis resource = jedisPool.getResource();
        //执行操作
        Boolean exists = resource.exists(key);
        resource.close();
        return exists;
    }

    @Override
    public Long expire(String key, int seconds) {
        //1.先得到jedis
        Jedis resource = jedisPool.getResource();
        //2.执行操作
        Long expire = resource.expire(key,seconds);
        resource.close();
        return expire;
    }

    @Override
    public Long ttl(String key) {
        //1.先得到jedis
        Jedis resource = jedisPool.getResource();
        //2.执行操作
        Long ttl = resource.ttl(key);
        resource.close();
        return ttl;
    }

    @Override
    public Long incr(String key) {
        //1.先得到jedis
        Jedis resource = jedisPool.getResource();
        //2.执行操作
        Long incr = resource.incr(key);
        resource.close();
        return incr;
    }

    @Override
    public Long hset(String key, String field, String value) {
        //1.先得到jedis
        Jedis resource = jedisPool.getResource();
        //2.执行操作
        Long hset = resource.hset(key,field,value);
        resource.close();
        return hset;
    }

    @Override
    public String hget(String key, String field) {
        //1.先得到jedis
        Jedis resource = jedisPool.getResource();
        //2.执行操作
        String hget = resource.hget(key,field);
        resource.close();
        return hget;
    }

    @Override
    public Long hdel(String key, String... field) {
        //1.先得到jedis
        Jedis resource = jedisPool.getResource();
        //2.执行操作
        Long hdel = resource.hdel(key,field);
        resource.close();
        return hdel;
    }
}
