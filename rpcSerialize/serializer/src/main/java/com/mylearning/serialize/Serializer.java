package com.mylearning.serialize;


import com.mylearning.commom.message.Message;

public interface Serializer {
    public <T> byte[] serializeObject(T object);

    public <T> T deserializeObject(Class<T> clazz,byte[] bytes);

    public Byte getType();
}
