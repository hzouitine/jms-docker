package demo;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Main {

	public static final String QUEUE = "TEST";

	public static void main(String[] args) throws JMSException {

		Session session = getSession();
		send(session);
		receive(session);
		closeConnection(session);
	}

	public static Session getSession() throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://localhost:61616");

		Connection connection = connectionFactory.createConnection();
		connection.start();

		return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	}

	private static void closeConnection(Session session) throws JMSException {

		session.close();
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.close();

	}

	public static void send(Session session) throws JMSException {

		Destination destination = session.createQueue(QUEUE);
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		String text = "Hello world!";
		TextMessage message = session.createTextMessage(text);
		producer.send(message);
		System.out.println("Sent: " + text);
	}

	public static void receive(Session session) throws JMSException {

		Destination destination = session.createQueue(QUEUE);
		MessageConsumer consumer = session.createConsumer(destination);
		Message message = consumer.receive();
		if (message instanceof TextMessage) {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			System.out.println("Received: " + text);
		} else {
			System.out.println("Received: " + message);
		}
		consumer.close();
	}

}
