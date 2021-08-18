package com.mylearning.server.netty.client.factory;

import com.mylearning.commom.config.Config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;


public class ProxyFactory {

    private final HashMap<Class<?>,Object> proxyObjectMap = new HashMap<>();
    private static final ProxyFactory INSTANCE = new ProxyFactory();

    /**
     * 对象工厂在初始化时,完成对代理类对象的创建
     */
    private ProxyFactory(){
        Map<String, String> customerServiceMap = Config.getCustomerServiceMap();
        for(Map.Entry<String, String> entry : customerServiceMap.entrySet()){
            try {
                Class<?> aClass = Class.forName(entry.getValue());
                Object object = getProxyService(aClass);
                proxyObjectMap.put(aClass,object);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("客户端工厂找不到类" + e);
            }
        }
    }


    /**
     * 有待补充,通过JDK代理,创建代理类对象,
     * 在增强方法中,需要通过单例channel向服务端发送请求,实现rpc调用,需要考虑多个对象抢占channel的线程安全问题
     * @param aClass
     * @return
     */
    private Object getProxyService(Class<?> aClass) {
        ClassLoader classLoader = aClass.getClassLoader();
        Class<?>[] interfaces = {aClass};
        return Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Class<?> aClass1 = proxy.getClass();
                Class<?> aClass2 = aClass;
                System.out.println(method.getName());

                return null;
            }
        });
    }

    public static ProxyFactory getInstance(){
        return INSTANCE;
    }

    public <T> T getObject(Class<T> tClass){
        return (T) proxyObjectMap.get(tClass);
    }
}
