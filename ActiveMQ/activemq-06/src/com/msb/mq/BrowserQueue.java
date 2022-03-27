package com.msb.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

public class BrowserQueue {

    public static void main(String[] args) throws JMSException, InterruptedException {
        // 1.获取连接工厂117802
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "admin", //ActiveMQConnectionFactory.DEFAULT_USER,
                "admin", //ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://yun-pc:61616");
        // 2.获取一个ActiveMQ的连接
        Connection connection = connectionFactory.createConnection();
        // *********特别注意，consumer消费端需要加启动方法************
        connection.start();
        // 3.获取session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4.找目的地，获取destination,消费端，也会从这个目的地取消息
        Queue queue = session.createQueue("userV1");

        QueueBrowser browser = session.createBrowser(queue);
        Enumeration enumeration = browser.getEnumeration();
        while (enumeration.hasMoreElements()) {
            Object obj = enumeration.nextElement();
            if(obj instanceof TextMessage){
                String text = ((TextMessage) obj).getText();
                System.out.println(text);
            }else if (obj instanceof MapMessage){
                MapMessage message = (MapMessage) obj;
                System.out.println(message.getString("name"));
            }else{
                System.out.println("类型未定义");
            }
        }
        // 6. 关闭连接
        // connection.close();

        System.out.println("AcitveMQ exit...");
    }
}
