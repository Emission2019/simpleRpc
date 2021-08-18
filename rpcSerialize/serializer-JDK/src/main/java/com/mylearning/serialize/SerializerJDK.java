package com.mylearning.serialize;

import java.io.*;


public class SerializerJDK implements Serializer{
    private static final byte TYPE_NUMBER = 0x03;

    @Override
    public <T> byte[] serializeObject(T object) {
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(bos)){
            objectOutputStream.writeObject(object);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("JDK,反序列化失败");
        }
    }

    @Override
    public <T> T deserializeObject(Class<T> clazz, byte[] bytes) {
        try (
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)
                ){
            return (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("JDK,反序列化失败");
        }
    }

    @Override
    public Byte getType() {
        return TYPE_NUMBER;
    }
}
