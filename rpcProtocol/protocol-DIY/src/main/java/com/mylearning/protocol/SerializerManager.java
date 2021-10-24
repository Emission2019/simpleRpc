package com.mylearning.protocol;

import com.mylearning.commom.message.Message;
import com.mylearning.serialize.Serializer;
import com.mylearning.commom.config.Config;

import java.util.*;

public class SerializerManager {

    private final static SerializerManager INSTANCE = new SerializerManager();
    private HashMap<String,Serializer> serializerMap = new HashMap<>();
    private HashMap<Byte,Serializer> typeMap = new HashMap<>();

    static {
        INSTANCE.loadSerializer();
    }
    private SerializerManager(){

    }

    public static SerializerManager getInstance(){
        return INSTANCE;
    }

    private void loadSerializer(){
        ServiceLoader<Serializer> serviceLoader = ServiceLoader.load(Serializer.class);
        Iterator<Serializer> iterator = serviceLoader.iterator();
        while (iterator.hasNext()){
            Serializer serializer = iterator.next();
            String name = serializer.getClass().getSimpleName();
            serializerMap.put(name,serializer);
            typeMap.put(serializer.getType(),serializer);
        }
    }

    public Serializer getSerializer(String name){
        return serializerMap.get("Serializer" + name);
    }

    public Serializer getSerializerByType(byte b){
        return typeMap.get(b);
    }


}
