package com.mylearning.serialize;


import com.google.gson.*;
import com.mylearning.commom.message.Message;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public class SerializerGson implements Serializer{

    private static final byte TYPE_NUMBER = 0x01;

    @Override
    public <T> byte[] serializeObject(T object) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassCodec()).create();
        String json = gson.toJson(object);
        return json.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public <T> T deserializeObject(Class<T> clazz, byte[] bytes) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassCodec()).create();
        String json = new String(bytes,StandardCharsets.UTF_8);
        return gson.fromJson(json,clazz);
    }

    static class ClassCodec implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>> {

        @Override
        public Class<?> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            try {
                String string = jsonElement.getAsString();
                return Class.forName(string);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
        }

        @Override
        public JsonElement serialize(Class<?> aClass, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(aClass.getName());
        }
    }

    @Override
    public Byte getType() {
        return TYPE_NUMBER;
    }
}
