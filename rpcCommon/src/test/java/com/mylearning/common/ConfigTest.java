package com.mylearning.common;

import com.mylearning.commom.config.Config;
import org.junit.Test;

import java.util.Map;

public class ConfigTest {

    @Test
    public void testProviderServiceMap(){
        Map<String, String> providerServiceMap = Config.getProviderServiceMap();
        for(Map.Entry<String,String> entry: providerServiceMap.entrySet()){
            System.out.println(entry);
        }
    }
}
