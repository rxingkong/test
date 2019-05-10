package com.qianfeng.rabbitmq.work;




import com.qianfeng.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by jackiechan on 19-4-30/下午12:09
 *
 * @Author jackiechan
 */
public class HelloWorldConsumer {
    private static final String QUEUENAME = "mianhuaiwangzewei";//队列的名字,也就是相当于我们发送短信的时候的手机号

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(1);//削峰 可以认为是限流,我每次只会从服务器上面取一条消息来处理,等我处理完成给服务器应答之后,服务器才会给第二条消息
        channel.queueDeclare(QUEUENAME,true,false,false,null);//声明队列,如果队列不存在,会创建队列
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //处理消息的方法
                String message = new String(body);
                System.out.println("111111====>" + message);
                //应答
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck( envelope.getDeliveryTag(),false);//true代表拒收消息,false代表应答消息
            }
        };
        channel.basicConsume(QUEUENAME, false, defaultConsumer);
    }
}
