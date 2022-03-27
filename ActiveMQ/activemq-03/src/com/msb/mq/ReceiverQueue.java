package com.msb.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ReceiverQueue {

    public static void main(String[] args) throws JMSException, InterruptedException {
        // 1.获取连接工厂117802
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "admin", //ActiveMQConnectionFactory.DEFAULT_USER,
                "admin", //ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://yun-pc:61616");
        // 1.2 序列化自定义对象时，需要加入信任列表
        connectionFactory.setTrustedPackages(new ArrayList(Arrays.asList(Girl.class.getPackage().getName())));
        // 2.获取一个ActiveMQ的连接
        Connection connection = connectionFactory.createConnection();
        // *********特别注意，consumer消费端需要加启动方法************
        connection.start();
        System.out.println("ReceiverQueue-1 week:5 start:");
        // 3.获取session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4.找目的地，获取destination,消费端，也会从这个目的地取消息
        Queue queue = session.createQueue("userV1");
        // 5.1 消息消费者
        // 分组、筛选
        String selector1 = "age > 90";
        String selector2 = "week = 5";
        MessageConsumer consumer = session.createConsumer(queue,selector1);
        consumer.setMessageListener(new MyListener());
        // consumer -> 消费者
        // producer -> 生产者
        /*while (true){
            TextMessage message = (TextMessage)consumer.receive();
            System.out.println("consumer message:" + message.getText());
            Thread.sleep(1000);
        }*/

        // 6. 关闭连接
        //connection.close();

        //System.out.println("AcitveMQ exit...");
    }
}
