package com.mylearning.server.netty.server.manager;



import com.mylearning.commom.message.Message;
import com.mylearning.protocol.Protocol;
import com.mylearning.commom.config.Config;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ServiceLoader;

public class ProtocolManager {

    private final static ProtocolManager INSTANCE = new ProtocolManager();
    private final HashMap<String,Protocol> protocolMap = new HashMap<>();
    private Protocol protocol;

    static {
        INSTANCE.loadProtocol();
    }
    private ProtocolManager(){

    }

    public static ProtocolManager getInstance(){
        return INSTANCE;
    }


    private void chooseProtocol(){
        String protocolName = "Protocol" + Config.getProtocol();
        protocol = protocolMap.get(protocolName);
    }

    public LengthFieldBasedFrameDecoder getFrameDecoder(){
        return protocol.getFrameDecoder();
    }

    public MessageToMessageCodec<ByteBuf, Message> getCodec(){
        return protocol.getCodec();
    }
    private void loadProtocol(){
        ServiceLoader<Protocol> serviceLoader = ServiceLoader.load(Protocol.class);
        Iterator<Protocol> iterator = serviceLoader.iterator();
        while (iterator.hasNext()){
            Protocol protocol = iterator.next();
            String name = protocol.getClass().getSimpleName();
            protocolMap.put(name,protocol);
        }
        chooseProtocol();
    }
}
