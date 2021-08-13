package com.mylearning.commom.message;

import com.mylearning.commom.message.messageImpl.RpcRequestMessage;
import com.mylearning.commom.message.messageImpl.RpcResponseMessage;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Message{
    protected final static byte RPC_REQUEST_MESSAGE = 0x01;
    protected final static byte RPC_RESPONSE_MESSAGE = 0x02;
    private final static HashMap<Byte,Class<? extends Message>> MESSAGE_MAP = new HashMap<>();

    private static final AtomicInteger id = new AtomicInteger(0);

    public static int nextId(){return id.getAndIncrement();}

    private int sequenceId;

    public Message() {
    }



    static {
        MESSAGE_MAP.put(RPC_REQUEST_MESSAGE, RpcRequestMessage.class);
        MESSAGE_MAP.put(RPC_RESPONSE_MESSAGE, RpcResponseMessage.class);
    }

    public abstract byte getMessageType();

    public static Class<? extends Message> getMessageClassByType(byte type){
        return MESSAGE_MAP.get(type);
    }

    public int getSequenceId(){
        return this.sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }
}
