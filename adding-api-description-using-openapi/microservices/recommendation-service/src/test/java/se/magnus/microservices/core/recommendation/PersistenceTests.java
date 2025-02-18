package se.magnus.microservices.core.recommendation;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import se.magnus.microservices.core.recommendation.persistence.RecommendationEntity;
import se.magnus.microservices.core.recommendation.persistence.RecommendationRepository;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class PersistenceTests extends MongoDbTestBase{

    @Autowired
    private RecommendationRepository repository;

    private RecommendationEntity savedEntity;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
        RecommendationEntity entity = new RecommendationEntity(1, 1, "hop", 5, "great");
        savedEntity = repository.save(entity);
        assertEqualRecommendation(entity, savedEntity);
    }

    @Test
    void create(){
        RecommendationEntity entity = new RecommendationEntity(1, 2, "hop", 4, "good");
        repository.save(entity);
        assertEquals(2, repository.count());
        assertEqualRecommendation(entity, repository.findById(entity.getId()).get());
    }

    @Test
    void update(){
        String newAuthor = "Lisa";
        savedEntity.setAuthor(newAuthor);
        repository.save(savedEntity);
        RecommendationEntity foundEntity = repository.findById(savedEntity.getId()).get();
        assertEquals(1, foundEntity.getVersion());
        assertEquals(newAuthor, foundEntity.getAuthor());
    }

    @Test
    void delete(){
        repository.delete(savedEntity);
        assertFalse(repository.existsById(savedEntity.getId()));
    }

    @Test
    void getProductById(){
        List<RecommendationEntity> entityList = repository.findByProductId(savedEntity.getProductId());
        assertEquals(1, entityList.size());
        assertEqualRecommendation(savedEntity, entityList.get(0));
    }

//    still not work
//    @Test
//    void duplicateError(){
//        repository.deleteAll();
//        assertThrows(DuplicateKeyException.class, () -> {
//            RecommendationEntity entity = new RecommendationEntity(1, 1, "hop", 5, "great");
//            repository.save(entity);
//            RecommendationEntity entity2 = new RecommendationEntity(1, 1, "hop", 5, "great");
//            repository.save(entity2);
//
//            System.out.printf("================");
//
//            List<RecommendationEntity> entityList = repository.findByProductId(1);
//            entityList.forEach(e -> {
//                System.out.println("RecommendationEntity{" +
//                        "id='" + e.getId() + '\'' +
//                        ", version=" + e.getVersion() +
//                        ", productId=" + e.getProductId() +
//                        ", recommendationId=" + e.getRecommendationId() +
//                        ", author='" + e.getAuthor() + '\'' +
//                        ", rating=" + e.getRating() +
//                        ", content='" + e.getContent() + '\'' +
//                        '}');
//
//            });
//
//            System.out.printf("================");
//        });
//    }

    @Test
    void optimisticLockError(){
        RecommendationEntity entity1 = repository.findById(savedEntity.getId()).get();
        RecommendationEntity entity2 = repository.findById(savedEntity.getId()).get();
        String newAuthor = "Jack";
        entity1.setAuthor(newAuthor);
        repository.save(entity1);

        assertThrows(OptimisticLockingFailureException.class, () -> {
            entity2.setAuthor("Jill");
            repository.save(entity2);
        });

        RecommendationEntity updatedEntity = repository.findById(entity1.getId()).get();
        assertEquals(newAuthor, updatedEntity.getAuthor());
        assertEquals(1, updatedEntity.getVersion());
    }



    private void assertEqualRecommendation(RecommendationEntity expectedEntity, RecommendationEntity actualEntity) {
        assertEquals(expectedEntity.getId(),               actualEntity.getId());
        assertEquals(expectedEntity.getVersion(),          actualEntity.getVersion());
        assertEquals(expectedEntity.getProductId(),        actualEntity.getProductId());
        assertEquals(expectedEntity.getRecommendationId(), actualEntity.getRecommendationId());
        assertEquals(expectedEntity.getAuthor(),           actualEntity.getAuthor());
        assertEquals(expectedEntity.getRating(),           actualEntity.getRating());
        assertEquals(expectedEntity.getContent(),          actualEntity.getContent());
    }
}
