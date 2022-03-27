package com.msb.mq;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class SenderQueue {

    public static void main(String[] args) throws JMSException, InterruptedException {
        // 1.获取连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "admin", //ActiveMQConnectionFactory.DEFAULT_USER,
                "admin", //ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://yun-pc:61616");
        // 1.2 序列化自定义对象时，需要加入信任列表
        // connectionFactory.setTrustedPackages(new ArrayList(Arrays.asList(Girl.class.getPackage().getName())));
        // 2.获取一个ActiveMQ的连接
        Connection connection = connectionFactory.createConnection();
        // 3.获取session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4.找目的地，获取destination,消费端，也会从这个目的地取消息
        Destination queue = session.createQueue("userV1");
        // 5.1 消息创建者
        MessageProducer producer = session.createProducer(queue);
        // consumer -> 消费者
        // producer -> 生产者
        // 5.2 创建消息
        TextMessage textMessage = session.createTextMessage(new Date().toString().substring(9));
        // 5.3 向目的地写入消息
        producer.send(textMessage);

       /* Girl girl = new Girl("lei", 16, 99.0);
        ObjectMessage objectMessage = session.createObjectMessage(girl);
        // producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT); // 不持久化
        producer.setTimeToLive(2000); // 超时时间 ms
        producer.send(objectMessage);*/


        MapMessage mapMessage = session.createMapMessage();
        mapMessage.setString("name","lucy");
        mapMessage.setBoolean("yihun",false);
        mapMessage.setInt("age", 17);
        producer.send(mapMessage);
        // 6. 关闭连接
        connection.close();

        System.out.println("AcitveMQ exit...");
    }
}
