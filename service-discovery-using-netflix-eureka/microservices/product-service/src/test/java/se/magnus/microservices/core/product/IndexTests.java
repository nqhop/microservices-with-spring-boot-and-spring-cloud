package se.magnus.microservices.core.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.test.StepVerifier;
import se.magnus.microservices.core.product.persistence.ProductEntity;
import se.magnus.microservices.core.product.persistence.ProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IndexTests extends MongoDbTestBase{

    @Autowired
    private ReactiveMongoOperations mongoTemplate;

    @Autowired
    private ProductRepository repository;

    @Test
    void verifyMongoTemplateExists() {
        assertNotNull   (mongoTemplate, "MongoTemplate should be available!");
    }

    @Test
    void checkIndexes() {
        StepVerifier.create(mongoTemplate.indexOps(ProductEntity.class).getIndexInfo())
                .expectNextMatches(index -> index.getName().equals("_id_"))
                .expectNextMatches(index -> index.getName().contains("productId"))
                .verifyComplete();
    }

    @Test
    void duplicateProductIdError(){
        ProductEntity product1 = new ProductEntity(1, "name1", 1);
        ProductEntity product2 = new ProductEntity(1, "name1", 1);

        StepVerifier.create(repository.save(product1))
                .expectNextMatches(saved -> saved.getProductId() == 1)
                .verifyComplete();

        StepVerifier.create(repository.save(product2))
                .expectError(DuplicateKeyException.class) // Should fail with this error
                .verify();
    }
}
