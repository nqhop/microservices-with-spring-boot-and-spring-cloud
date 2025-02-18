package se.magnus.microservices.core.product;

import org.springframework.dao.DuplicateKeyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import se.magnus.microservices.core.product.persistence.ProductEntity;
import se.magnus.microservices.core.product.persistence.ProductRepository;
import static org.springframework.data.domain.Sort.Direction.ASC;

import java.util.DuplicateFormatFlagsException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class PersistenceTests extends MongoDbTestBase{

    @Autowired
    private ProductRepository repository;
    private ProductEntity savedEntity;

    @BeforeEach
    void setupDb(){
        repository.deleteAll();
        ProductEntity entity = new ProductEntity(1, "n", 1);
        savedEntity = repository.save(entity);
        assertEqualsProduct(entity, savedEntity);
    }

    @Test
    void create(){
        ProductEntity newEntity = new ProductEntity(2, "n", 2);
        repository.save(newEntity);

        ProductEntity foundEntity = repository.findById(newEntity.getId()).get();
        assertEqualsProduct(newEntity, foundEntity);
        assertEquals(2, repository.count());
    }

    @Test
    void update(){
        savedEntity.setName("n2");
        repository.save(savedEntity);

        ProductEntity foundEntity = repository.findById(savedEntity.getId()).get();
        assertEquals(1, (int)foundEntity.getVersion());
        assertEquals("n2", foundEntity.getName());
    }

    @Test
    void delete(){
        repository.delete(savedEntity);
        assertFalse(repository.existsById(savedEntity.getId()));
    }

    @Test
    void getByProductId(){
        Optional<ProductEntity> foundEntity = repository.findByProductId(savedEntity.getProductId());
        assertTrue(foundEntity.isPresent());
        assertEqualsProduct(foundEntity.get(), savedEntity);
    }

//    @Test
//    void duplicateError(){
//        assertThrows(DuplicateKeyException.class, () -> {
//            System.out.println("first entity: " + repository.count());
//            repository.findAll().forEach(savedEntity -> {
////                System.out.println("productId:" + savedEntity.getProductId());
//                System.out.println(savedEntity.toString());
//            });
//            ProductEntity entity = new ProductEntity(savedEntity.getProductId(), "n", 2);
//            System.out.println("new entity: " + entity.toString());
//            System.out.println("check: " + (savedEntity.getProductId() == entity.getProductId()));
//            repository.save(entity);
//            repository.save(entity);
//            repository.save(entity);
//            System.out.println("\nsecound entity: " + repository.count());
//            repository.findAll().forEach(savedEntity -> {
//                System.out.println(savedEntity.toString());
//            });
//        });
//    }

    @Test
    void optimisticLockError(){
        ProductEntity entity1 = repository.findById(savedEntity.getId()).get();
        ProductEntity entity2 = repository.findById(savedEntity.getId()).get();

        entity1.setName("n1");
        repository.save(entity1);

        assertThrows(OptimisticLockingFailureException.class, () -> {
            entity2.setName("n22");
            repository.save(entity2);
        });

        ProductEntity updatedEmtity = repository.findById(savedEntity.getId()).get();
        assertEquals(1, (int)updatedEmtity.getVersion());
        assertEquals("n1", updatedEmtity.getName());
    }

    @Test
    void paging(){
        repository.deleteAll();
        List<ProductEntity> newProducts = IntStream.rangeClosed(101, 110)
                .mapToObj(i -> new ProductEntity(i, "name" + 1, i))
                .toList();

        repository.saveAll(newProducts);

        Pageable nextPage = PageRequest.of(0, 4, ASC, "productId");
        nextPage = testNextPage(nextPage, "[101, 102, 103, 104]", true);
        nextPage = testNextPage(nextPage, "[105, 106, 107, 108]", true);
        nextPage = testNextPage(nextPage, "[109, 110]", false);

    }

    private Pageable testNextPage(Pageable nextPage, String expectedProductIds, boolean expectsNextPage) {
        Page<ProductEntity> productPage = repository.findAll(nextPage);
        assertEquals(expectedProductIds, productPage.getContent()
        .stream().map(ProductEntity::getProductId).toList().toString());
        assertEquals(expectsNextPage, productPage.hasNext());
        return productPage.nextPageable();
    }

    private void assertEqualsProduct(ProductEntity expectedEntity, ProductEntity savedEntity) {
        assertEquals(expectedEntity.getId(), savedEntity.getId());
        assertEquals(expectedEntity.getName(), savedEntity.getName());
        assertEquals(expectedEntity.getProductId(), savedEntity.getProductId());
        assertEquals(expectedEntity.getVersion(), savedEntity.getVersion());
        assertEquals(expectedEntity.getWeight(), savedEntity.getWeight());
    }


}
