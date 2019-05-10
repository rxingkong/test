package com.qianfeng.rabbitmq.simple;

import com.qianfeng.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class HelloWorldConsumer {
    private static final String QUEUENAME="lishun";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUENAME,true,false,false,null);//声明队列,如果队列不存在,会创建队列
        QueueingConsumer consumer=new QueueingConsumer(channel);
        //参数1是哪个对列,参数2是否是自动应答
        channel.basicConsume(QUEUENAME,true,consumer);
        while (true){
            QueueingConsumer.Delivery delivery=consumer.nextDelivery();
            byte[] body=delivery.getBody();
            System.out.println(System.currentTimeMillis()+"收到消息:====>"+new String(body));
        }
    }
}
