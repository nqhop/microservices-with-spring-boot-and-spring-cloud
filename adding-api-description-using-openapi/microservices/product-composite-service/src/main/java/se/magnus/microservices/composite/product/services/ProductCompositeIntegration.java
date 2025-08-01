package se.magnus.microservices.composite.product.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import se.magnus.api.core.product.Product;
import se.magnus.api.core.product.ProductService;
import se.magnus.api.core.recommendation.Recommendation;
import se.magnus.api.core.recommendation.RecommendationService;
import se.magnus.api.core.review.Review;
import se.magnus.api.core.review.ReviewService;
import se.magnus.api.exceptions.InvalidInputException;
import se.magnus.api.exceptions.NotFoundException;
import se.magnus.util.http.HttpErrorInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Component
public class ProductCompositeIntegration implements ProductService, RecommendationService, ReviewService {
    private static final Logger LOG = LoggerFactory.getLogger(ProductCompositeIntegration.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    private final String productServiceUrl;
    private final String recommendationServiceUrl;
    private final String reviewServiceUrl;

    @Autowired
    public ProductCompositeIntegration(
            RestTemplate restTemplate,
            ObjectMapper mapper,
//            @Value("localhost") String productServiceHost,
//            @Value("7001") String productServicePort,
//            @Value("localhost") String recommendationServiceHost,
//            @Value("7002") String recommendationServicePort,
//            @Value("localhost") String reviewServiceHost,
//            @Value("7003") String reviewServicePort) {
            @Value("${app.product-service.host}") String productServiceHost,
            @Value("${app.product-service.port}") int productServicePort,
            @Value("${app.recommendation-service.host}") String recommendationServiceHost,
            @Value("${app.recommendation-service.port}") int recommendationServicePort,
            @Value("${app.review-service.host}") String reviewServiceHost,
            @Value("${app.review-service.port}") int reviewServicePort) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;

        productServiceUrl = "http://" + productServiceHost + ":" + productServicePort + "/product/";
        recommendationServiceUrl = "http://" + recommendationServiceHost + ":" + recommendationServicePort + "/recommendation";
        reviewServiceUrl = "http://" + reviewServiceHost + ":" + reviewServicePort + "/review";
    }

    @Override
    public Product createProduct(Product body) {
        try{
            String url = productServiceUrl;
            LOG.debug("Will post a new product to URL: {}", url);

            Product product = restTemplate.postForObject(url, body, Product.class);
            LOG.debug("Created a product with id: {} ", product.getProductId());
            return product;
        }catch (HttpClientErrorException e) {
            throw handleHttpClientException(e);
        }
    }

    @Override
    public void deleteProduct(int productId) {
        restTemplate.delete(productServiceUrl + "/" + productId);

    }

    @Override
    public Product getProduct(int productId) {
        try {
            String url = productServiceUrl + productId;
            LOG.debug("url for getProduct: {} ", url);
            return restTemplate.getForObject(url, Product.class);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }
    }

    private String getErrorMessage(HttpClientErrorException ex) {
        try {
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        } catch (IOException ioex) {
            return ex.getMessage();
        }
    }


    @Override
    public Recommendation createRecommendation(Recommendation body) {
        try{
            String url = recommendationServiceUrl;
            LOG.debug("Will post a new recommend to URL: {}", url);

            Recommendation recommend = restTemplate.postForObject(url, body, Recommendation.class);
            LOG.debug("Created a recommend with id: {} ", recommend.getProductId());
            return recommend;
        }catch (HttpClientErrorException e) {
            throw handleHttpClientException(e);
        }
    }

    @Override
    public void deleteRecommendation(int productId) {
        restTemplate.delete(recommendationServiceUrl + "/" + productId);
    }

    @Override
    public List<Recommendation> getRecommendations(int productId) {
        try {
            String url = recommendationServiceUrl + productId;

            LOG.debug("Will call getRecommendations API on URL: {}", url);
            List<Recommendation> recommendations = restTemplate
                    .exchange(url, GET, null, new ParameterizedTypeReference<List<Recommendation>>() {})
                    .getBody();

            LOG.debug("Found {} recommendations for a product with id: {}", recommendations.size(), productId);
            return recommendations;

        } catch (Exception ex) {
            LOG.warn("Got an exception while requesting recommendations, return zero recommendations: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Review> getReviews(int productId) {
        try {
            String url = reviewServiceUrl + productId;
            LOG.debug("Will call getReviews API on URL: {}", url);
            List<Review> reviews =  restTemplate
                    .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {})
                    .getBody();
            LOG.debug("Found {} reviews for a product with id: {}", reviews.size(), productId);
            return reviews;
        }catch (Exception ex) {
            LOG.warn("Got an exception while requesting reviews, return zero reviews: {}", ex.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Review createReview(Review body) {
        try{
            String url = reviewServiceUrl;
            LOG.debug("Will post a new review to URL: {}", url);
            Review review = restTemplate.postForObject(url, body, Review.class);
            LOG.debug("Created a review with id: {} ", review.getProductId());
            return review;
        }catch (HttpClientErrorException e) {
            throw handleHttpClientException(e);
        }

    }

    @Override
    public void deleteReviews(int productId) {
        restTemplate.delete(reviewServiceUrl + "/" + productId);
    }


    private RuntimeException handleHttpClientException(HttpClientErrorException ex) {
        switch (HttpStatus.resolve(ex.getStatusCode().value())) {
            case NOT_FOUND:
                throw new NotFoundException(getErrorMessage(ex));

            case UNPROCESSABLE_ENTITY:
                throw new InvalidInputException(getErrorMessage(ex));

            default:
                LOG.warn("Got an unexpected HTTP error: {}, will rethrow it", ex.getStatusCode());
                LOG.warn("Error body: {}", ex.getResponseBodyAsString());
                throw ex;
        }
    }

    @Override
    public String greeting() {
        LOG.debug("ProductCompositeIntegration: greeting url {}", recommendationServiceUrl + "/greeting");
        String recommendationGreeting = restTemplate.getForObject(recommendationServiceUrl + "/greeting", String.class);
        return "productCompositeGreeting" + "---" + recommendationGreeting;
    }
}
