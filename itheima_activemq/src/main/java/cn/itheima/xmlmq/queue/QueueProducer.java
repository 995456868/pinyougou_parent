package cn.itheima.xmlmq.queue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/queue/applicationContext-jms-consumer.xml")
public class QueueProducer {
  @Autowired
  private JmsTemplate jmsTemplate;
  @Autowired
  private Destination queueTextDestination;
  @Test
  public void send(final String text){
    jmsTemplate.send(queueTextDestination, new MessageCreator() {
      @Override
      public Message createMessage(Session session) throws JMSException {
         return session.createTextMessage(text);
      }
    });
  }
}
