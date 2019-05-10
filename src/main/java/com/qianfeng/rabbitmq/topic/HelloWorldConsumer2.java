package com.qianfeng.rabbitmq.topic;

//
//                            _ooOoo_  
//                           o8888888o  
//                           88" . "88  
//                           (| -_- |)  
//                            O\ = /O  
//                        ____/`---'\____  
//                      .   ' \\| |// `.  
//                       / \\||| : |||// \  
//                     / _||||| -:- |||||- \  
//                       | | \\\ - /// | |  
//                     | \_| ''\---/'' | |  
//                      \ .-\__ `-` ___/-. /  
//                   ___`. .' /--.--\ `. . __  
//                ."" '< `.___\_<|>_/___.' >'"".  
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |  
//                 \ \ `-. \_ __\ /__ _/ .-` / /  
//         ======`-.____`-.___\_____/___.-`____.-'======  
//                            `=---='  
//  
//         .............................................  
//                  佛祖镇楼                  BUG辟易  
//          佛曰:  
//                  写字楼里写字间，写字间里程序员；  
//                  程序人员写程序，又拿程序换酒钱。  
//                  酒醒只在网上坐，酒醉还来网下眠；  
//                  酒醉酒醒日复日，网上网下年复年。  
//                  但愿老死电脑间，不愿鞠躬老板前；  
//                  奔驰宝马贵者趣，公交自行程序员。  
//                  别人笑我忒疯癫，我笑自己命太贱；  
//  


import com.qianfeng.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by jackiechan on 19-4-30/下午12:09
 *
 * @Author jackiechan
 */
public class HelloWorldConsumer2 {
    private static final String EXCHANGENAME = "liuxiangtopic";//交换机的名字
    private static final String QUEUENAME = "renburugou";//队列的名字,也就是相当于我们发送短信的时候的手机号

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //不管在什么模式中,消费者永远只和队列通信,所以消费者这边永远要声明队列
        channel.queueDeclare(QUEUENAME,false,false,false,null);//声明队列,如果队列不存在,会创建队列
        //绑定队列到交换机
        //先声明交换机
        channel.exchangeDeclare(EXCHANGENAME,"topic");//声明交换机,类型是fanout类型
        channel.queueBind(QUEUENAME, EXCHANGENAME, "order.*");//绑定到交换机上面
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //处理消息的方法
                String message = new String(body);
                System.out.println("order222222====>" + message);
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
