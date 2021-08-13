package com.mylearning.serialize;


import com.mylearning.commom.message.Message;

public class SerializerProtoBuf implements Serializer{

    private static final byte TYPE_NUMBER = 0x02;


    @Override
    public <T> byte[] serializeObject(T object) {
        return new byte[0];
    }

    @Override
    public <T> T deserializeObject(Class<T> clazz, byte[] bytes) {
        return null;
    }

    @Override
    public Byte getType() {
        return TYPE_NUMBER;
    }
}
