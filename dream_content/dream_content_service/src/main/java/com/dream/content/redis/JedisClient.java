package com.dream.content.redis;

public interface JedisClient {
    //    新增键值对
    String set(String key, String value);

    //    根据key得到value
    String get(String key);

    //    判断key是否存在
    Boolean exists(String key);

    //    设置key的过期时间 单位秒
    Long expire(String key, int seconds);

    //    获取key的剩余时间
    Long ttl(String key);

    //    将对应的key的value值加一，如果key不错在，则加入该key，值设置为一
    Long incr(String key);

    //    一个key对应一个value，这个value是map，一个map中又是一个键值对
    Long hset(String key, String field, String value);

    //    获取hash为key的数据中的field的value
    String hget(String key, String field);

    /*  从hash中删除一个或多个field
    string... field 可以是多个String参数
    hdel（"demo","field1"）
    hdel("demo","field1","field2")
      */
    Long hdel(String key, String... field);
}
