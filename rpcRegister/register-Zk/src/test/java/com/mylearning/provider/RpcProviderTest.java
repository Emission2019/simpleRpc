package com.mylearning.provider;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.net.util.IPAddressUtil;

import java.net.InetAddress;

public class RpcProviderTest {

    private CuratorFramework client;

    private String serviceName = "com.mylearning.service.TestService";
    /**
     * 建立连接
     */
    @Before
    public void connect(){
        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(1000, 1);
        client = CuratorFrameworkFactory.builder().connectString("192.168.32.131:2181").
                sessionTimeoutMs(5 * 1000).connectionTimeoutMs(5 * 1000).retryPolicy(retry).namespace("rpc").build();
        // 开启连接
        client.start();
    }


    /**
     * 注册服务
     */
    @Test
    public void register() throws Exception {
        // 将服务名中的 . 替换为 /
        String ip = InetAddress.getLocalHost().getHostAddress();

        String path = "/" + serviceName.replace('.', '/') + "/" + ip;
        // 创建临时顺序结点
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);

        int t = 0;
    }


    /**
     * 断开连接
     */
    @After
    public void close(){
        if(client == null) client.close();
    }
}
