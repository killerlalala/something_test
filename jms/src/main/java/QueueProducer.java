import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.ConnectionFactory;

public class QueueProducer {
    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");

}
