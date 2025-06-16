package se.magnus.microservices.core.review;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.org.yaml.snakeyaml.constructor.DuplicateKeyException;
import se.magnus.microservices.core.review.persistence.ReviewEntity;
import se.magnus.microservices.core.review.persistence.ReviewRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

@DataJpaTest
@Transactional(propagation = NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersistenceTests extends MySqlTestBase{
    @Autowired
    private ReviewRepository repository;

    private ReviewEntity savedReview;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
        ReviewEntity entity = new ReviewEntity(1, 1, "Doo", "Geography", "greet");
        savedReview = repository.save(entity);
        assertEqualReview(entity, savedReview);
    }

    @Test
    void create(){
        ReviewEntity entity = new ReviewEntity(1, 2, "Doo", "Geography", "exellend");
        repository.save(entity);
        assertEquals(2, repository.count());
        assertEqualReview(entity, repository.findById(entity.getId()).get());
    }

    @Test
    void update(){
        String newAuthor = "Rose";
        savedReview.setAuthor(newAuthor);
        repository.save(savedReview);
        ReviewEntity foundEntity = repository.findById(savedReview.getId()).get();
        assertEquals(1, foundEntity.getVersion());
        assertEquals(newAuthor, foundEntity.getAuthor());
    }

    @Test
    void delete(){
        repository.delete(savedReview);
        assertFalse(repository.existsById(savedReview.getId()));
    }

    @Test
    void getByProductById(){
        List<ReviewEntity> entityList = repository.findByProductId(savedReview.getProductId());
        assertEquals(1, entityList.size());
        assertEqualReview(savedReview, entityList.get(0));
    }

    @Test
    void duplicateError(){
        assertThrows(DataIntegrityViolationException.class, () -> {
            ReviewEntity entity = new ReviewEntity(1, 1, "Doo", "Geography", "perfect");
            repository.save(entity);
        });
    }

    @Test
    void optimisticLockError(){
        ReviewEntity entity1 = repository.findById(savedReview.getId()).get();
        ReviewEntity entity2 = repository.findById(savedReview.getId()).get();

        String newAuthor = "Json";
        entity1.setAuthor(newAuthor);
        repository.save(entity1);

        assertThrows(OptimisticLockingFailureException.class, () -> {
            entity2.setAuthor("Jin");
            repository.save(entity2);
        });

        ReviewEntity updatedEntity = repository.findById(entity1.getId()).get();
        assertEquals(1, updatedEntity.getVersion());
        assertEquals(newAuthor, updatedEntity.getAuthor());
    }

    private void assertEqualReview(ReviewEntity expectedEntity, ReviewEntity actualEntity) {
        assertEquals(expectedEntity.getId(), actualEntity.getId());
        assertEquals(expectedEntity.getReviewId(), actualEntity.getReviewId());
        assertEquals(expectedEntity.getContent(), actualEntity.getContent());
        assertEquals(expectedEntity.getVersion(), actualEntity.getVersion());
        assertEquals(expectedEntity.getAuthor(), actualEntity.getAuthor());
    }
}
