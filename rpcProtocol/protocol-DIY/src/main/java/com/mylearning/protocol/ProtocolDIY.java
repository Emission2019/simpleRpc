package com.mylearning.protocol;

import com.mylearning.commom.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageCodec;

public class ProtocolDIY implements Protocol {

    private final static MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();

    @Override
    public LengthFieldBasedFrameDecoder getFrameDecoder() {
        return new ProtocolFrameDecoder();
    }

    @Override
    public MessageToMessageCodec<ByteBuf, Message> getCodec() {
        return MESSAGE_CODEC;
    }


}
