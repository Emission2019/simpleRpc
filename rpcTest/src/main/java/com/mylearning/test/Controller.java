package com.mylearning.test;

import com.mylearning.server.netty.client.Client;
import com.mylearning.test.service.GoodByeWorld;
import com.mylearning.test.service.HelloWorld;

import java.util.concurrent.locks.ReentrantLock;

public class Controller {


    public static void main(String[] args) {

        Client client = new Client();
        HelloWorld helloWorld = client.getInstance(HelloWorld.class);
        GoodByeWorld goodByeWorld = client.getInstance(GoodByeWorld.class);
        helloWorld.sayHello();
        goodByeWorld.sayGoodBye();
    }
}
