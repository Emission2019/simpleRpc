package com.mylearning.register;

public interface Provider {


    /**
     * 初始化
     */
    public void init();

    /**
     * 向注册中心注册服务
     */
    public void register();

    /**
     * 关闭服务
     */
    public void close();
}
