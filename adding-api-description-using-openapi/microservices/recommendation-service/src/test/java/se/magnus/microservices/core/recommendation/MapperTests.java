package se.magnus.microservices.core.recommendation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import se.magnus.api.core.recommendation.Recommendation;
import se.magnus.microservices.core.recommendation.persistence.RecommendationEntity;
import se.magnus.microservices.core.recommendation.services.RecommendationMapper;

import java.util.Collections;
import java.util.List;

public class MapperTests {
    private final RecommendationMapper mapper = Mappers.getMapper(RecommendationMapper.class);

    @Test
    void testMapper(){
        Recommendation api = new Recommendation(1, 1, "hop", 5, "great", "sa");

        RecommendationEntity entity = mapper.apiToEntity(api);

        assertEquals(api.getProductId(), entity.getProductId());
        assertEquals(api.getRecommendationId(), entity.getRecommendationId());
        assertEquals(api.getAuthor(), entity.getAuthor());
        assertEquals(api.getRate(), entity.getRating());
        assertEquals(api.getContent(), entity.getContent());

        Recommendation api2 = mapper.entityToApi(entity);
        assertEquals(api.getProductId(), api2.getProductId());
        assertEquals(api.getRecommendationId(), api2.getRecommendationId());
        assertEquals(api.getAuthor(), api2.getAuthor());
        assertEquals(api.getRate(), api2.getRate());
        assertEquals(api.getContent(), api2.getContent());
        assertNull(api2.getServiceAddress());
    }

    @Test
    void testMapperList(){
        Recommendation api = new Recommendation(1, 1, "hop", 5, "great", "sa");
        List<RecommendationEntity> entityList = mapper.apiListToEntityList(Collections.singletonList(api));

        assertEquals(1, entityList.size());

        List<Recommendation> apiList = mapper.entityListToApiList(entityList);

        assertEquals(1, apiList.size());
    }
}
