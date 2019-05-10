package com.qianfeng.rabbitmq.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {
    private static ConnectionFactory connectionFactory;
    static {
        //按照面向对象,要么是创建一个对象,要么是静态方法,要么是工厂,要么是build模式
        connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("rabbitmq.qfjava.cn");
        connectionFactory.setPort(8800);
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("test");
        connectionFactory.setVirtualHost("/test");
    }
    public static Connection getConnection() throws IOException, TimeoutException {
        return connectionFactory.newConnection();
    }
}
