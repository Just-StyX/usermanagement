package jsl.security.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/*
* Does not need to lie in this application.
* Put in a separate application to wait for the messages
 */
public class EmailConsumer {
    private final Logger logger = LoggerFactory.getLogger(EmailConsumer.class);

    public void receive() throws IOException, TimeoutException {
        var queueName = "message";
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);
        logger.info(" [*] Waiting for message. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            var message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            logger.info(" [x] Received {}", message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
