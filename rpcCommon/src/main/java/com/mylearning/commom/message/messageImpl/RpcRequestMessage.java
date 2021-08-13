package com.mylearning.commom.message.messageImpl;

import com.mylearning.commom.message.Message;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 客户端通过JDK代理发请求消息
 * 服务端通过反射来进行调用：
 *  - Object service = ServicesFactory.getService(Class.forName(msg.getInterfaceName()));
 *  - Method method = service.getClass().getMethod(msg.getMethodName(), msg.getParameterTypes());
 *  - Object invoke = method.invoke(service, msg.getParameterValue());
 */
public class RpcRequestMessage extends Message {
    /**
     * 调用接口的全限定类名
     */
    private String interfaceName;

    /**
     * 调用接口中的方法名
     */
    private String methodName;

    /**
     * 方法返回类型
     */
    private Class<?> returnType;


    /**
     * 方法参数类型数组
     */
    private Class<?>[] parameterType;


    /**
     * 传入参数数组
     */
    private Object[] parameterValue;


    public RpcRequestMessage(int sequenceId,String interfaceName, String methodName, Class<?> returnType, Class<?>[] parameterType, Object[] parameterValue) {
        super.setSequenceId(sequenceId);
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameterType = parameterType;
        this.parameterValue = parameterValue;

    }



    @Override
    public byte getMessageType() {
        return RPC_REQUEST_MESSAGE;
    }
}
