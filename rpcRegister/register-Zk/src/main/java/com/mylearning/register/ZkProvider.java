package com.mylearning.register;

import com.mylearning.commom.config.Config;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetAddress;
import java.util.Map;

public class ZkProvider implements Provider {

    private CuratorFramework client;



    /**
     * 启动ZkProvider
     * 具体参数先写死,后面写在配置里面
     */
    @Override
    public void init(){
        if(this.client != null) return;
        ExponentialBackoffRetry retry = new ExponentialBackoffRetry(1000, 1);
        String registerInfo = Config.getRegisterAddress() + ":" + Config.getRegisterPort();
        client = CuratorFrameworkFactory.builder().connectString(registerInfo).
                sessionTimeoutMs(5 * 1000).connectionTimeoutMs(5 * 1000).retryPolicy(retry).namespace("rpc").build();
        client.start();
    }

    /**
     * 从配置文件中的ProviderServiceMap获取信息,向注册中心注册服务
     */
    @Override
    public void register() {
        Map<String, String> serviceMap = Config.getProviderServiceMap();
        for(String name: serviceMap.keySet()){
            try {
                String ip = InetAddress.getLocalHost().getHostAddress();
                String path = "/" + name.replace('.', '/') + "/" + ip;
                client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close(){
        if(client != null) client.close();
    }

}
