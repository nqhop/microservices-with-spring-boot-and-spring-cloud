package com.example.magnus;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CoursePublisherTests {

    @EnableAutoConfiguration
    public static class EmptyConfiguration {}

    @Test
    public void sampleTest(){
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
                TestChannelBinderConfiguration.getCompleteConfiguration(
                        EmptyConfiguration.class))
                .web(WebApplicationType.NONE)
                .run("--spring.jmx.enabled=false")) {

            StreamBridge streamBridge = context.getBean(StreamBridge.class);
            MessagePublisher messagePublisher = new MessagePublisher(streamBridge);
            messagePublisher.publishMessage("Hello");
            OutputDestination output = context.getBean(OutputDestination.class);
            Message<byte[]> result = output.receive(100, "test-out");

            assertThat(result).isNotNull();
            assertThat(new String(result.getPayload())).isEqualTo("Hello");
        }
    }
}

