package com.mylearning.commom.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public abstract class Config {
    private static Properties properties;

    /**
     * 从配置文件中解析服务器暴露的服务列表
     */
    private static volatile Map<String, String> providerServiceMap;
    private static volatile Map<String, String> customerServiceMap;

    private static final Object LOCK = new Object();


    static {
        try(InputStream in = Config.class.getResourceAsStream("/rpcConfig.properties")){
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 懒加载单例,获得服务端暴露服务列表
     * 放入map之前需要去除"rpcProviderService"前缀
     * @return
     */
    public static Map<String, String> getProviderServiceMap(){
        if( providerServiceMap == null){
            synchronized (LOCK){
                if(providerServiceMap == null){
                    providerServiceMap = new HashMap<>();
                    Set<String> nameSet = properties.stringPropertyNames();
                    Iterator<String> iterator = nameSet.iterator();
                    while (iterator.hasNext()){
                        String name = iterator.next();
                        if(name.startsWith("rpcProviderService")){
                            providerServiceMap.put(name.substring(19),properties.getProperty(name));
                        }
                    }
                }
            }
        }
        return providerServiceMap;
    }

    /**
     * 懒加载单例,获得客户端需要创建的代理对象
     * 放入map之前需要去除"rpcCustomerService"前缀
     * @return
     */
    public static Map<String, String> getCustomerServiceMap(){
        if( customerServiceMap == null){
            synchronized (LOCK){
                if(customerServiceMap == null){
                    customerServiceMap = new HashMap<>();
                    Set<String> nameSet = properties.stringPropertyNames();
                    Iterator<String> iterator = nameSet.iterator();
                    while (iterator.hasNext()){
                        String name = iterator.next();
                        if(name.startsWith("rpcCustomerService")){
                            customerServiceMap.put(name.substring(19),properties.getProperty(name));
                        }
                    }
                }
            }
        }
        return customerServiceMap;
    }
    public static String getSerialization(){
        return properties.getProperty("serialization");
    }

    public static String getProtocol(){
        return properties.getProperty("protocol");
    }

    public static String getPort(){
        return properties.getProperty("port");
    }

    public static String getRegisterAddress(){return properties.getProperty("register.address");}

    public static String getRegisterPort(){return (String) properties.getOrDefault("register.port","2181");}

}
