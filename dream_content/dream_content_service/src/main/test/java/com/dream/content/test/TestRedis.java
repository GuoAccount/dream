package com.dream.content.test;

import com.dream.content.redis.JedisClient;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;

public class TestRedis {
    @Test
    public void testRedisClient(){
        //因为使用的是接口，不管哪个实现类实现的方法一样所以测试方法一样
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext-redis.xml");
        JedisClient bean = ctx.getBean(JedisClient.class);
        //获取JedisClient接口的对象 向上造型 父类的引用指向子类的对象 接口对象是接口引用指向其实现类 这里就是获取指向的实现类
        //集体使用哪个实现类 里面会自动获取 ，所以两个实现类不能同时存在，不然会冲突
        String set = bean.set("jedisClient", "hahhahah");
        String jedisClient = bean.get("jedisClient");
        System.out.println(jedisClient);
    }
    @Test
    public void testRedis(){
        //创建一个Jedis对象（相当于jdbc中的connection对象）需要指定Ip和端口
        Jedis jedis = new Jedis("192.168.31.12", 6379);
        jedis.set("demo","java");
        jedis.hset("demo1","file2","2");
        String demo = jedis.get("demo");
        System.out.println(demo);
        //关闭
        jedis.close();
    }
    @Test
    public void testRedisCluster(){
        //使用JedisCluster对象，需要指定一个set集合作为redis集群的节点对象
        HashSet<HostAndPort> nodes = new HashSet<>();
        //每一个HostAndPort都是一个ip和端口描述的redis服务端的节点
        nodes.add(new HostAndPort("192.168.31.12",7001));
        nodes.add(new HostAndPort("192.168.31.12",7002));
        nodes.add(new HostAndPort("192.168.31.12",7003));
        nodes.add(new HostAndPort("192.168.31.12",7004));
        nodes.add(new HostAndPort("192.168.31.12",7005));
        nodes.add(new HostAndPort("192.168.31.12",7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        jedisCluster.set("cluster_demo","java_jedis_cluster");
        String demo = jedisCluster.get("cluster_demo");
        System.out.println(demo);
        //3关闭
        jedisCluster.close();
    }
}
