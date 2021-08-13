package com.mylearning.provider;

import com.mylearning.commom.config.Config;
import com.mylearning.register.Customer;
import com.mylearning.register.ZkCustomer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class RpcCustomerTest {
    
    @Test
    public void testRpcCustomerService() throws Exception {
        Customer zkCustomer = new ZkCustomer();
        zkCustomer.init();
        System.out.println(zkCustomer.loadService("com.mylearning.test.service.ServiceTest"));
        System.out.println(zkCustomer.loadService("com.mylearning.test.service.ServiceTest1"));
        zkCustomer.close();
    }
}
