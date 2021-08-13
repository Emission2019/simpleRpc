package com.mylearning.register;

import java.util.List;
import java.util.Set;

public interface Customer {
    /**
     * 从注册中心拉取服务
     * @return
     */
    public List<String> loadService(String className);

    /**
     * 启动
     */
    public void init();

    /**
     * 关闭
     */
    public void close();
}
