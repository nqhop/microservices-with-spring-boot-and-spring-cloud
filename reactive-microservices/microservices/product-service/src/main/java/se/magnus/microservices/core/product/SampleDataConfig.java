package se.magnus.microservices.core.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import se.magnus.api.core.product.Product;
import se.magnus.microservices.core.product.persistence.ProductEntity;
import se.magnus.microservices.core.product.persistence.ProductRepository;

import java.util.List;

@Configuration
public class SampleDataConfig {

    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceApplication.class);
    @Bean
    CommandLineRunner initDatabase(ProductRepository repository, ReactiveMongoOperations mongoTemplate) {
        return args -> {
            mongoTemplate.dropCollection(Product.class).block();

            List<ProductEntity> products = List.of(
                    new ProductEntity(101, "Laptop", 2000),
                    new ProductEntity(102, "Smartphone", 180),
                    new ProductEntity(103, "Tablet", 500),
                    new ProductEntity(104, "Monitor", 3500),
                    new ProductEntity(105, "Keyboard", 700)
            );
            repository.saveAll(products).subscribe();
            LOG.info("SampleDataConfig: Created " + products.size() + " products");

        };
    }
}
