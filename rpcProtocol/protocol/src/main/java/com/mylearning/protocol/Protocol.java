package com.mylearning.protocol;

import com.mylearning.commom.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageCodec;

public interface Protocol{
    public LengthFieldBasedFrameDecoder getFrameDecoder();
    public MessageToMessageCodec<ByteBuf, Message> getCodec();

}
