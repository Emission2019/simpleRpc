package com.mylearning.provider;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ZkWatcherTest {


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
        client = CuratorFrameworkFactory.builder().connectString("192.168.32.131:2181").
                sessionTimeoutMs(5 * 1000).connectionTimeoutMs(5 * 1000).retryPolicy(retry).namespace("rpc").build();
        // 开启连接
        client.start();

    }


    @After
    public void close(){
        if(client!=null) client.close();;
    }

    @Test
    public void testNodeCache() throws Exception {
        // 1. 创建NodeCache对象
        NodeCache nodeCache = new NodeCache(client, "/app1");

        // 2. 注册监听
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("节点变化了~");

                byte[] data = nodeCache.getCurrentData().getData();
                System.out.println(new String(data));
            }
        });

        // 3.开启监听.如果设置为true,则开启监听时,加载缓冲数据
        nodeCache.start();

        while(true){

        }
    }


    /**
     * 演示 PathChildrenCache:监听某个节点的所有子节点们
     * @throws Exception
     */
    @Test
    public void testPathChildrenCache() throws Exception {
        // 1. 创建监听对象
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client,"/app2",true);


        // 2. 绑定监听器
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
                System.out.println("子节点变化了");
                System.out.println(event);
            }
        });

        // 3.开启监听.如果设置为true,则开启监听时,加载缓冲数据
        pathChildrenCache.start();

        while(true){

        }
    }
}
