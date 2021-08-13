package com.mylearning.provider;

import com.mylearning.register.Provider;
import com.mylearning.register.ZkProvider;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.net.util.IPAddressUtil;

import java.net.InetAddress;


/**
 * 在debug时注意,断点设置选择暂停当前线程,不会心跳线程也会被暂停
 */
public class RpcProviderTest {

    
    @Test
    public void providerTest(){
        Provider zkProvider = new ZkProvider();
        zkProvider.init();
        zkProvider.register();
        zkProvider.close();
    }
}
