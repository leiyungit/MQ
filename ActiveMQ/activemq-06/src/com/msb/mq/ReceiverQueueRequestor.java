package com.msb.mq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

public class ReceiverQueueRequestor {

    public static void main(String[] args) throws JMSException, InterruptedException {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "admin", //ActiveMQConnectionFactory.DEFAULT_USER,
                "admin", //ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                "tcp://yun-pc:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("userV1");

        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    System.out.println("consumer收到消息："+((TextMessage)message).getText());
                    Destination replyTo = message.getJMSReplyTo();
                    System.out.println("replyTo:"+replyTo);
                    MessageProducer producer = session.createProducer(replyTo);
                    producer.send(session.createTextMessage("ack"));
                } catch (JMSException e) {
                    e.printStackTrace();
                }

            }
        });

        // 6. 关闭连接
        // connection.close();

        System.out.println("AcitveMQ exit...");
    }
}
