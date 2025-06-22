package se.magnus.microservices.core.review;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import se.magnus.api.core.review.Review;
import se.magnus.microservices.core.review.persistence.ReviewEntity;
import se.magnus.microservices.core.review.services.ReviewMapper;

public class MapperTests {
    private final ReviewMapper mapper = Mappers.getMapper(ReviewMapper.class);

    @Test
    void testMapper(){
        Review api = new Review(1, 1, "John", "Geography", "oke", "sa");

        ReviewEntity enrity = mapper.apiToEntity(api);

        assertEquals(api.getProductId(), enrity.getProductId());
        assertEquals(api.getReviewId(), enrity.getReviewId());
        assertEquals(api.getAuthor(), enrity.getAuthor());
        assertEquals(api.getSubject(), enrity.getSubject());
        assertEquals(api.getContent(), enrity.getContent());

        Review api2 = mapper.entityToApi(enrity);
        assertEquals(api.getProductId(), api2.getProductId());
        assertEquals(api.getReviewId(), api2.getReviewId());
        assertEquals(api.getAuthor(), api2.getAuthor());
        assertEquals(api.getSubject(), api2.getSubject());
        assertEquals(api.getContent(), api2.getContent());
        assertNull(api2.getServiceAddress());
    }
}
