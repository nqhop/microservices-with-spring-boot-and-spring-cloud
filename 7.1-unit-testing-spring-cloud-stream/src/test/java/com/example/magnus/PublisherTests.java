package com.example.magnus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
public class PublisherTests {

    @Autowired
    private OutputDestination outputDestination;

    @Autowired
    private MessagePublisher messagePublisher;

    @Test
    void TestMessagePublisher() {
        String testMessage = "TestMessage";

        messagePublisher.publishMessage(testMessage);

        Message<byte[]> receivedMessage = outputDestination.receive(1000,"test-out");


        Message<byte[]> received = outputDestination.receive(1000);
        System.out.println("Received: " + (received == null ? "null" : new String(received.getPayload())));


        assertThat(receivedMessage).isNotNull();
        assertEquals(testMessage, new String(receivedMessage.getPayload()));
    }
}
