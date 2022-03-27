package com.msb.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Receiver2 {

    public static void main(String[] args) throws JMSException, InterruptedException {
        // 1.获取连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnectionFactory.DEFAULT_USER,
                ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://yun-pc:61616");
        // 2.获取一个ActiveMQ的连接
        Connection connection = connectionFactory.createConnection();
        // *********特别注意，consumer消费端需要加启动方法************
        connection.start();
        // 3.获取session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 4.找目的地，获取destination,消费端，也会从这个目的地取消息
        Queue queue = session.createQueue("userV1");
        // 5.1 消息消费者
        MessageConsumer consumer = session.createConsumer(queue);
        // consumer -> 消费者
        // producer -> 生产者
        while (true){
            TextMessage message = (TextMessage)consumer.receive();
            System.out.println("consumer message:" + message.getText());
            Thread.sleep(1000);
        }

        // 6. 关闭连接
        //connection.close();

        //System.out.println("AcitveMQ exit...");
    }
}
