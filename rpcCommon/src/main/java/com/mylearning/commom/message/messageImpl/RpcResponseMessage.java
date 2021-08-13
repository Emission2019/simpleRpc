package com.mylearning.commom.message.messageImpl;

import com.mylearning.commom.message.Message;


public class RpcResponseMessage extends Message {

    /**
     * 返回值大小
     */
    private Object resultValue;

    /**
     *
     * 异常值
     */
    private Exception exception;

    public RpcResponseMessage() {
        super();
    }


    public Object getResultValue() {
        return resultValue;
    }

    public void setResultValue(Object resultValue) {
        this.resultValue = resultValue;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public byte getMessageType() {
        return RPC_RESPONSE_MESSAGE;
    }
}
