package se.magnus.microservices.composite.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.magnus.api.composite.product.ProductAggregate;
import se.magnus.api.core.product.Product;
import se.magnus.api.event.Event;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static reactor.core.publisher.Mono.just;
import static se.magnus.api.event.Event.Type.CREATE;
import static se.magnus.microservices.composite.product.IsSameEvent.sameEventExceptCreatedAt;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {"eureka.client.enabled=false"})
@Import({TestChannelBinderConfiguration.class})
public class MessagingTests {

    private static final Logger LOG = LoggerFactory.getLogger(MessagingTests.class);

    @Autowired
    private WebTestClient client;

    @Autowired
    private OutputDestination target;

    @BeforeEach
    void setUp() {
        purgeMessages("products");
        purgeMessages("recommendations");
        purgeMessages("reviews");
    }

    @Test
    void createCompositeProduct1(){
        ProductAggregate composite = new ProductAggregate(1, "name", 1, null, null, null);
        PostAndVerifyProduct(composite, ACCEPTED);

        final List<String> productMessages = getMessages("products");
        final List<String> recommendationMessages = getMessages("recommendations");
        final List<String> reviewMessages = getMessages("reviews");

        assertEquals(1, productMessages.size());

        Event<Integer, Product> expectedEvent =
        new Event<>(new Product(composite.getProductId(), composite.getName(), composite.getWeight(), null), composite.getProductId(), CREATE);
//        assertThat(productMessages.get(0), is(sameEventExceptCreatedAt(expectedEvent)));

        assertEquals(0, recommendationMessages.size());
        assertEquals(0, reviewMessages.size());

    }

    //    use to purge a topic from all current messages
    private void purgeMessages(String bindingName){
        getMessages(bindingName);
    }

//    use the getMessage() method to return all messages in a topic
    private List<String> getMessages(String bindingName){
        List<String> messages = new ArrayList<>();
        boolean anyMoreMessages = true;

        while(anyMoreMessages){
            Message<byte[]> message = getMessage(bindingName);
            if (message == null){
                anyMoreMessages = false;
            }else{
                messages.add(new String(message.getPayload()));
            }
        }
        return messages;
    }

    // method returns a message from a specified topic
    private Message<byte[]> getMessage(String bindingName){
        try {
            return target.receive(0, bindingName);
        }catch(NullPointerException e){
            LOG.error("getMessage() received a NPE with binding = {}",
                    bindingName);
            return null;
        }
    }

    private void PostAndVerifyProduct(ProductAggregate composite, HttpStatus excectedStatus) {
        client.post().uri("/product-composite")
                .body(just(composite), ProductAggregate.class)
                .exchange()
                .expectStatus().isEqualTo(excectedStatus);
    }
}
