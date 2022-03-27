package com.msb.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sender {

    public static void main(String[] args) throws JMSException, InterruptedException {
        // 1.获取连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "admin", //ActiveMQConnectionFactory.DEFAULT_USER,
                "admin", //ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://yun-pc:61616");
        // 2.获取一个ActiveMQ的连接
        Connection connection = connectionFactory.createConnection();
        // 3.获取session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4.找目的地，获取destination,消费端，也会从这个目的地取消息
        Queue queue = session.createQueue("userV1");
        // 5.1 消息创建者
        MessageProducer producer = session.createProducer(queue);
        // consumer -> 消费者
        // producer -> 生产者
        /*// 5.2 创建消息
        TextMessage textMessage = session.createTextMessage(new Date().toString().substring(9));
        // 5.3 向目的地写入消息
        producer.send(textMessage);*/
        SimpleDateFormat sdf = new SimpleDateFormat("ssSSS");
        for (int i = 0; i < 100; i++) {
            Date date = new Date();
            String format = sdf.format(date);
            // System.out.println(format);
            TextMessage textMessage = session.createTextMessage(i+","+format);
           /* if(i%4==0)
                textMessage.setJMSPriority(9); // 设置无效
                */
//            if(i%4==0)
//                producer.send(textMessage,DeliveryMode.PERSISTENT,1,1000*100);
//            else
            producer.setPriority(7);
                producer.send(textMessage);
            // Thread.sleep(200);
        }


        // 6. 关闭连接
        connection.close();

        System.out.println("AcitveMQ exit...");
    }
}
