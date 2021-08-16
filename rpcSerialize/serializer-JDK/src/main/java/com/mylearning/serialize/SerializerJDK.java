package com.mylearning.serialize;

public class SerializerJDK implements Serializer{
    private static final byte TYPE_NUMBER = 0x03;

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
        return null;
    }
}
