package jsl.security.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class EmailProducer {
    private final Logger logger = LoggerFactory.getLogger(EmailProducer.class);

    public void sendMessage(String message) {
        var queueName = "message";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()){
            channel.queueDeclare(queueName, false, false, false, null);
            channel.basicPublish("", queueName, null, message.getBytes());
            logger.info(" [x] Sent {}", message);
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
