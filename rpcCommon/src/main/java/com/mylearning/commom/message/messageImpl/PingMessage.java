package com.mylearning.commom.message.messageImpl;

import com.mylearning.commom.message.Message;

public class PingMessage extends Message {

    @Override
    public byte getMessageType() {
        return 0;
    }
}
