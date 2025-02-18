package se.magnus.microservices.core.recommendation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.shaded.com.fasterxml.jackson.core.PrettyPrinter;
import se.magnus.api.core.recommendation.Recommendation;
import se.magnus.microservices.core.recommendation.persistence.RecommendationRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Mono.just;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecommendationServiceApplicationTests extends MongoDbTestBase{

    @Autowired
    private WebTestClient client;

    @Autowired
    private RecommendationRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }


    @Test
    void getRecommendationsByProductId(){
        int productId = 1;
        postAndVerifyRecommendation(productId, 1, OK);
        postAndVerifyRecommendation(productId, 2, OK);
        postAndVerifyRecommendation(productId, 3, OK);

        assertEquals(3, repository.count());

        getAndVerifyRecommendationsByProductId(productId, OK)
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$[2].productId").isEqualTo(productId)
                .jsonPath("$[2].recommendationId").isEqualTo(3);
    }

//    @Test
//    void duplicateError(){
//        int productId = 1;
//        int recommendationId = 1;
//        postAndVerifyRecommendation(productId, recommendationId, OK)
//                .jsonPath("$.productId").isEqualTo(productId)
//                .jsonPath("$.recommendationId").isEqualTo(recommendationId);
//
//        assertEquals(1, repository.count());
//
//        postAndVerifyRecommendation(productId, recommendationId, UNPROCESSABLE_ENTITY)
//                .jsonPath("$.path").isEqualTo("/recommendation")
//                .jsonPath("$.message").isEqualTo("Duplicate recommendation");
//
//        assertEquals(1, repository.count());
//    }

    @Test
    void getRecommendationsMissingParameter(){
        getAndVerifyRecommendationsByProductId("", BAD_REQUEST)
            .jsonPath("$.path").isEqualTo("/recommendation")
            .jsonPath("$.message").isEqualTo("Required query parameter 'productId' is not present.");

    }

    @Test
    void getRecommendationsInvalidParameter(){
        getAndVerifyRecommendationsByProductId("?productId=no-integer", BAD_REQUEST)
            .jsonPath("$.path").isEqualTo("/recommendation")
            .jsonPath("$.message").isEqualTo("Type mismatch.");
    }

    @Test
    void getRecommendationsNotFound(){
        int productIdNotFound = 113;
        getAndVerifyRecommendationsByProductId(productIdNotFound, OK)
                .jsonPath("$.length()").isEqualTo(0);
    }

    @Test
    void getRecommendationsInvalidParameterNegativeValue(){
        int productIdInvalid = -13;
        getAndVerifyRecommendationsByProductId(productIdInvalid, UNPROCESSABLE_ENTITY)
            .jsonPath("$.path").isEqualTo("/recommendation")
            .jsonPath("$.message").isEqualTo("Invalid productId: " + productIdInvalid );
    }


    private WebTestClient.BodyContentSpec postAndVerifyRecommendation(int productId, int recommendationId, HttpStatus expectedStatus){
        Recommendation recommendation = new Recommendation(productId, recommendationId, "Jack", 5, "Excellent", "sa");
        return client.post()
                .uri("/recommendation")
                .body(just(recommendation), Recommendation.class)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }

    private WebTestClient.BodyContentSpec getAndVerifyRecommendationsByProductId(int productId, HttpStatus httpStatus){
        return getAndVerifyRecommendationsByProductId("?productId=" + productId, httpStatus);
    }

    private WebTestClient.BodyContentSpec getAndVerifyRecommendationsByProductId(String path, HttpStatus httpStatus) {
        return client.get()
                .uri("/recommendation" + path)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(httpStatus)
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody();
    }
}
