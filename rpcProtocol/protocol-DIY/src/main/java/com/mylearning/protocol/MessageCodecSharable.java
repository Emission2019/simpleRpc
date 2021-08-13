package com.mylearning.protocol;

import com.mylearning.commom.config.Config;
import com.mylearning.commom.message.Message;
import com.mylearning.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {

    private static final SerializerManager serializerManager = SerializerManager.getInstance();

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> list) throws Exception {
        ByteBuf byteBuf = channelHandlerContext.alloc().buffer();
        // 1. 4个字节魔术
        byteBuf.writeBytes("TELE".getBytes(StandardCharsets.UTF_8));
        // 2. 1个字节的版本
        byteBuf.writeByte(1);
        // 3. 序列化方式
        byteBuf.writeByte(serializerManager.getSerializer(Config.getSerialization()).getType());
        // 4. 1个字节的指令类型
        byteBuf.writeByte(message.getMessageType());
        // 5. 4个字节的指令请求序号
        byteBuf.writeInt(message.getSequenceId());
        // 无意义,对齐填充
        byteBuf.writeByte(0xff);
        // 6. 获取内容的字节数组
        byte[] bytes = serializerManager.getSerializer(Config.getSerialization()).serializeObject(message);

        byteBuf.writeInt(bytes.length);
        // 8. 写入内容
        byteBuf.writeBytes(bytes);

        list.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 1. 读取magicnumber
        byte[] magicNumber = new byte[4];
        byteBuf.readBytes(magicNumber);
        // 2. 读取版本号
        byte version = byteBuf.readByte();
        // 3. 读取序列化算法
        byte serializerType = byteBuf.readByte();
        Serializer serializer = serializerManager.getSerializerByType(serializerType);
        // 4. 读取消息类型
        byte messageType = byteBuf.readByte();
        // 5. 读取消息序列号
        int sequenceId = byteBuf.readInt();
        byteBuf.readByte();
        // 6. 读取消息长度及消息
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes,0,length);
        // 7. 获得消息类型
        Class<?> messageClass = Message.getMessageClassByType(messageType);
        // 8. 反序列化
        Object message = serializer.deserializeObject(messageClass, bytes);
        list.add(message);
    }

    public static void main(String[] args) {

    }
}
