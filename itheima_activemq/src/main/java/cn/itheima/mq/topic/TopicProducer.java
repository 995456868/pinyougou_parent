package cn.itheima.mq.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TopicProducer {
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("topic_test");
        MessageProducer producer = session.createProducer(topic);
        TextMessage message = session.createTextMessage("topic测试。。");
        producer.send(topic,message);
        producer.close();
        connection.close();
        session.close();
    }
}
