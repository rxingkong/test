package com.qianfeng.rabbitmq.work;



import com.qianfeng.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by jackiechan on 19-4-30/上午11:50
 *
 * @Author jackiechan
 */
public class HelloWorldProducer {
    private static final String QUEUENAME = "mianhuaiwangzewei";//队列的名字,也就是相当于我们发送短信的时候的手机号

    public static void main(String[] args) throws IOException, TimeoutException {
        //连接服务器
        Connection connection = ConnectionUtil.getConnection();//相当于我们链接数据库
        Channel channel = connection.createChannel();//这个相当于我们链接数据库时候的statement
        //标记
        /**
         * 参数1 队列的名字
         * 参数2 是否持久化,false代表消息在内存中,如果mq服务器重启消息会丢失,如果你想保存,那么必须声明为true,那么mq服务器会将消息保存到mq内部的数据中,注意,如果有两个地方都声明相同名字的队列,声明的参数必须完全一模一样
         * 参数3 是否排外(独有) 1,当链接关闭的时候,这个队列会自动删除,2这个队列是私有的,如果不是私有的,那么可以有多个消费者同时监听这个队列,如果是私有的,会被锁定,那么多个消费监听的时候会报错
         * 参数4 是否自动删除
         */
        channel.queueDeclare(QUEUENAME,true,false,false,null);//声明队列,如果队列不存在,会创建队列
        //要发送什么内容
        String message = "让我们深切缅怀王泽伟同志,王泽伟,伟大的无产阶级革命家,思想家,教育家,政治家,军事家22";
        for (int i = 0; i < 50; i++) {

        channel.basicPublish("", QUEUENAME, null, (message+"--->"+i).getBytes());
        System.out.println("发送消息成功:=====>" + message+"--->"+i);
        }
        //发送
        //参数1 交换机的名字,如果没有写"",参数2,路由的key,如果是交换机的时候写交换机指定的key,如果点对点,则写队列的名字
        channel.close();
        connection.close();
    }
}
