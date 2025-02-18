package se.magnus.microservices.core.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.magnus.api.core.product.Product;
import se.magnus.microservices.core.product.persistence.ProductRepository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static reactor.core.publisher.Mono.just;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests extends MongoDbTestBase{

    @Autowired
    private WebTestClient client;

    @Autowired
    private ProductRepository repository;

//    @BeforeEach
//    void setupDb(){
//        repository.deleteAll();
//    }
//
//    @Test
//    void getProductById(){
//        int productId = 1;
//
//        postAndVerifyProduct(productId, OK);
//        assertTrue(repository.findByProductId(productId).isPresent());
//
//        getAndVerifyProduct(productId, OK).jsonPath("$.productId").isEqualTo(productId);
//
//    }
//
//    @Test
//    void duplicateError(){
//        int productId = 1;
//        postAndVerifyProduct(productId, OK);
//        assertTrue(repository.findByProductId(productId).isPresent());
//        postAndVerifyProduct(productId, UNPROCESSABLE_ENTITY)
//                .jsonPath("$.path").isEqualTo("/product")
//                .jsonPath("$.message").isEqualTo("Duplicate key, Product Id: " +
//                        productId);
//    }
//
//    @Test
//    void deleteProduct(){
//        int productId = 1;
//        postAndVerifyProduct(productId, OK);
//        assertTrue(repository.findByProductId(productId).isPresent());
//
//        deleteAndVerifyProduct(productId, OK);
//        assertFalse(repository.findByProductId(productId).isPresent());
//
//        deleteAndVerifyProduct(productId, OK);
//    }
//
//
//
//    @Test
//    void getProductInvalidParameterString(){
//
//        getAndVerifyProduct("/invalid", BAD_REQUEST)
//                .jsonPath("$.path").isEqualTo("/product/invalid")
//                .jsonPath("$.message").isEqualTo("Type mismatch.");
//    }
//
//    @Test
//    void getProductNotFound(){
//        int productIdNotFound = 13;
//        getAndVerifyProduct(productIdNotFound, NOT_FOUND)
//            .jsonPath("$.path").isEqualTo("/product/" + productIdNotFound)
//            .jsonPath("$.message").isEqualTo("No product found for productId: " + productIdNotFound);
//    }
//
//    @Test
//    void getProductInvalidParameterNegativeValue(){
//        int productIdInvalid = -10;
//        getAndVerifyProduct(productIdInvalid, UNPROCESSABLE_ENTITY)
//            .jsonPath("$.path").isEqualTo("/product/" + productIdInvalid)
//            .jsonPath("$.message").isEqualTo("Invalid productId: " + productIdInvalid);
//    }
//
//    private WebTestClient.BodyContentSpec postAndVerifyProduct(int productId, HttpStatus expectedStatus){
//        Product product = new Product(productId, "Name " + productId, productId, "SA");
//        return client.post()
//                .uri("/product")
//                .body(just(product), Product.class)
//                .accept(APPLICATION_JSON)
//                .exchange()
//                .expectStatus().isEqualTo(expectedStatus)
//                .expectHeader().contentType(APPLICATION_JSON)
//                .expectBody();
//    }
//
//    private WebTestClient.BodyContentSpec deleteAndVerifyProduct(int productId, HttpStatus expectedStatus){
//        return client.delete()
//                .uri("/product" + productId)
//                .accept(APPLICATION_JSON)
//                .exchange()
//                .expectStatus().isEqualTo(expectedStatus)
//                .expectBody();
//    }
//
//    private WebTestClient.BodyContentSpec getAndVerifyProduct(int productId, HttpStatus expectedStatus){
//        return getAndVerifyProduct("/" + productId, expectedStatus);
//    }
//
//    private WebTestClient.BodyContentSpec getAndVerifyProduct(String path, HttpStatus expectedStatus){
//        return client.delete()
//                .uri(path)
//                .accept(APPLICATION_JSON)
//                .exchange()
//                .expectStatus().isEqualTo(expectedStatus)
//                .expectHeader().contentType(APPLICATION_JSON)
//                .expectBody();
//    }
}
