package com.taotao.rest.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;

/**
 * Created by h on 2017-03-05.
 */

public class JedisTest {
    @Test
    public void testJedisSingle(){
        //创建一个Jedis的对象
        Jedis jedis = new Jedis("192.168.109.128", 6379);
        //调用jedis对象的方法,方法名称和redis的命令一致
        jedis.set("key1", "Jedis Test");
       String a = jedis.get("key1");
        System.out.println(a);
        //关闭Jedis
        jedis.close();
    }

    /**
     * 使用连接池
     */
    @Test
    public void testJedisPool(){
        //创建jedis连接池
        JedisPool jedisPool = new JedisPool("192.168.109.128", 6379);
        //从连接池获得jedis对象
        Jedis jedis = jedisPool.getResource();
        String a = jedis.get("key1");
        System.out.println(a);
        //关闭jedis 关闭连接池
        jedis.close();
        jedisPool.close();
    }
    /**
     * 连接redis集群
     */
    @Test
    public void testJedisCluster(){
        HashSet<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("192.168.109.128", 7001));
        nodes.add(new HostAndPort("192.168.109.128", 7002));
        nodes.add(new HostAndPort("192.168.109.128", 7003));
        nodes.add(new HostAndPort("192.168.109.128", 7004));
        nodes.add(new HostAndPort("192.168.109.128", 7005));
        nodes.add(new HostAndPort("192.168.109.128", 7006));
        JedisCluster cluster = new JedisCluster(nodes);

        cluster.set("key1", "1");
        String a =cluster.get("key1");
        System.out.println(a);
        cluster.close();
    }

    @Test
    public void testSpringJedisSingle(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-jedis.xml");
    JedisPool pool= (JedisPool)  applicationContext.getBean("redisClient");
    Jedis jedis = pool.getResource();
        String a =jedis.get("key1");
        System.out.println(a);
        jedis.close();
        pool.close();
    }

    @Test
    public void testSpringJedis(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-jedis.xml");
        JedisCluster jedisCluster =(JedisCluster) applicationContext.getBean("redisClient");
        String a = jedisCluster.get("key1");
        System.out.println(a);
        jedisCluster.close();

    }
}
