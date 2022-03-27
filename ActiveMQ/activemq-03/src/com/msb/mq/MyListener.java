package com.msb.mq;

import javax.jms.*;

public class MyListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("----------");
        if(message instanceof ObjectMessage){

            try {
                Girl girl = (Girl) ((ObjectMessage) message).getObject();
                //Girl girl = (Girl)((ActiveMQObjectMessage)message).getObject();
                System.out.println(message);
                System.out.println(girl.getName());
                System.out.println(girl.getAge());
                System.out.println(girl.getPrice());

            } catch (JMSException e) {
                e.printStackTrace();
            }
        }else if(message instanceof MapMessage){
            try {
                String name = ((MapMessage) message).getString("name");
                Boolean yihun = ((MapMessage) message).getBoolean("yihun");
                int age = ((MapMessage) message).getInt("age");
                System.out.println(message);
                System.out.println("name:"+name);
                System.out.println("yihun:"+yihun);
                System.out.println("age:"+age);

            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }
}
