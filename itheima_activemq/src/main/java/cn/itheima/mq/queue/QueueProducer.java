package cn.itheima.mq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class QueueProducer {
    public static void main(String[] args) {
        try {
            //创建消息中间件工厂
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
            //创建连接
            Connection connection = factory.createConnection();
            //开启连接
            connection.start();
            //创建回话
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建消息体 指定消息模式
            Queue queue = session.createQueue("queue_test");
            //创建生产者
            MessageProducer producer = session.createProducer(queue);
            //创建消息类型
            TextMessage textMessage = session.createTextMessage("你好activeMQ");
            //生产者 发送消息
            producer.send(textMessage);
            //释放资源
            connection.close();
            producer.close();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
