package com.msb.mq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * 同步消息
 */
public class SenderQueueRequestor {

    public static void main(String[] args) throws JMSException, InterruptedException {
        // 1.获取连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "admin", //ActiveMQConnectionFactory.DEFAULT_USER,
                "admin", //ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://yun-pc:61616");
        ActiveMQConnection connection = (ActiveMQConnection)connectionFactory.createConnection();

        // 因为要接收回传，所以要添加启动
        connection.start();
        QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("userV1");
        // MessageProducer producer = session.createProducer(queue);
        TextMessage textMessage = session.createTextMessage(new Date().toString().substring(9));

        // 生产者替换对象
        QueueRequestor queueRequestor = new QueueRequestor(session, queue);
        // 发送消息的方式修改
        // producer.send(textMessage);
        System.out.println("sender 开始发送消息");
        TextMessage responseMsg = (TextMessage)queueRequestor.request(textMessage);
        System.out.println("sender 消息发送完成");
        System.out.println("sender 接收的cumsumer消息："+responseMsg.getText());
        // 6. 关闭连接
        // connection.close();

        System.out.println("AcitveMQ exit...");
    }
}
