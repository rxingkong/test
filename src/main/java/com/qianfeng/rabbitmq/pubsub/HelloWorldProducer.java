package com.qianfeng.rabbitmq.pubsub;

import com.qianfeng.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class HelloWorldProducer {
    private static final String EXCHANGENAME = "liuxiang";//交换机的名字
    public static void main(String[] args) throws IOException, TimeoutException {
        //链接服务器
        Connection connection = ConnectionUtil.getConnection();//相当于我们链接数据库
        Channel channel = connection.createChannel();//这个相当于我们链接数据库时候statement
        channel.exchangeDeclare(EXCHANGENAME,"fanout");//声明交换机,类型是fanout类型
        String message = "姓郑的,你脖子好了吗?好了就跟我一起摇摆,没好的话,我们就开心的唱歌了";
        channel.basicPublish(EXCHANGENAME,"",null,message.getBytes());
        System.out.println("发送消息成功"+message);
        channel.close();
        connection.close();
    }
}
