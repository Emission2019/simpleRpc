package com.mylearning.server.netty.client;

import com.mylearning.server.netty.client.factory.ProxyFactory;

public class Client {

    /**
     * 通过代理对象工厂,JDK代理,获得代理对象
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T getInstance(Class<T> tClass){
        return ProxyFactory.getInstance().getObject(tClass);
    }
}
