package com.mylearning.register;

import com.mylearning.commom.config.Config;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ZkCustomer implements Customer{

    private ConcurrentHashMap<String,List<String>> serviceMap = new ConcurrentHashMap<>();
    private CuratorFramework client;

    @Override
    public List<String> loadService(String className) {
        return serviceMap.get(className);
    }

    @Override
    public void init() {
        if(client != null) return;
        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(1000, 1);
        String registerInfo = Config.getRegisterAddress() + ":" + Config.getRegisterPort();
        client = CuratorFrameworkFactory.builder().connectString(registerInfo).
                sessionTimeoutMs(5 * 1000).connectionTimeoutMs(5 * 1000).retryPolicy(retry).namespace("rpc").build();
        client.start();
        initServiceMap();
    }

    /**
     * 首次初始化ServiceMap
     * 之后,考虑设置监听器,监视子节点变化,由此变化更新ServiceMap
     */
    public void initServiceMap(){
        Map<String, String> customerServiceMap = Config.getCustomerServiceMap();
        for(Map.Entry<String, String> entry: customerServiceMap.entrySet()){
            String path = "/" + entry.getValue().replace('.','/');
            try {
                List<String> list = client.getChildren().forPath(path);
                serviceMap.put(entry.getValue(),list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        client.close();
    }
}
