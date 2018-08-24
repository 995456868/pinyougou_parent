package cn.itheima.mq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueueConsumer {
    public static void main(String[] args) {

        try {
            //创建消息工厂
            ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
            //获取连接
            Connection connection = activeMQConnectionFactory.createConnection();
            //开启连接
            connection.start();
            //创建会话
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue("queue_test");
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {

                    try {
                        TextMessage textMessage = (TextMessage)message;
                        System.out.println(textMessage.getText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            System.in.read();
            session.close();
            connection.close();
            consumer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
