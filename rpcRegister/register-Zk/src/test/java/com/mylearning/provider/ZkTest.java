package com.mylearning.provider;

import com.mylearning.commom.config.Config;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ZkTest {

    private CuratorFramework client;

    /**
     * 建立连接
     */
    @Before
    public void testConnect(){
        // 第一种方式
        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(1000, 1);
//        CuratorFrameworkFactory.newClient("198.168.32.131",60*1000,15*1000,retry);
        // 第二种方式

        String address = Config.getRegisterAddress();
        String ip = Config.getRegisterPort();
        client = CuratorFrameworkFactory.builder().connectString(Config.getRegisterAddress()+":"+Config.getRegisterPort()).
                sessionTimeoutMs(5 * 1000).connectionTimeoutMs(5 * 1000).retryPolicy(retry).build();
        // 开启连接
        client.start();

    }

    /**
     * 创建结点： create 持久 临时 顺序  数据
     * 1.基本创建
     * 2.创建结点  带有数据
     * 3.设置结点的类型
     * 4.创建多级节点
     */
    @Test
    public void testCreate() throws Exception {
        // 1. 基本创建
        String s = client.create().forPath("/app1");
        System.out.println(s);
    }

    @Test
    public void testCreate1() throws Exception {

        String s = client.create().creatingParentsIfNeeded().forPath("/app2/hello");
        System.out.println(s);
    }


    /**
     * 查询节点：
     * 1、查询数据：get
     * 2、查询子节点： ls
     * 3、查询节点状态： ls -s
     * @throws Exception
     */
    @Test
    public void testGet1() throws Exception {
        // 1、查询数据:get
        byte[] s = client.getData().forPath("/app2/hello");
        System.out.println(new String(s));
    }

    @Test
    public void testGet2() throws Exception {
        // 2、查询子节点： ls
        List<String> s = client.getChildren().forPath("/");
        System.out.println(s);
    }

    @Test
    public void testGet3() throws Exception {
        // 3、查询节点状态： ls -s
        Stat status = new Stat();
        client.getData().storingStatIn(status).forPath("/app2/hello");
        System.out.println(status);
    }

    @After
    public void close(){
        if(client!=null) client.close();;
    }

}
