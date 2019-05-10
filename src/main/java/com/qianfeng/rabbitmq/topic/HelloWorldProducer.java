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
    private static final String EXCHANGENAME = "liuxiangtopic";//交换机的名字

    public static void main(String[] args) throws IOException, TimeoutException {
        //连接服务器
        Connection connection = ConnectionUtil.getConnection();//相当于我们链接数据库
        Channel channel = connection.createChannel();//这个相当于我们链接数据库时候的statement
        channel.exchangeDeclare(EXCHANGENAME,"topic");//声明交换机,类型是direct类型
        String message = "在有生的瞬间能遇到你,竟花光所有运气";
        //发送消息到队列,标记是mao
        channel.basicPublish(EXCHANGENAME, "order.delete.cancel", null, message.getBytes());
        System.out.println("发送消息成功" + message);
        channel.close();
        connection.close();
    }
}
