package se.magnus.microservices.composite.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class controller {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String productServiceUrl = "http://product:8080/";
    private final String recommendationServiceUrl = "http://recommendation:8080/";
    private final String reviewServiceUrl = "http://review:8080/";

    @GetMapping("/composite")
    public String composite() {

        String productGreeting = restTemplate.getForObject(productServiceUrl + "product", String.class);
        String recommendationGreeting = restTemplate.getForObject(recommendationServiceUrl + "recommendation", String.class);
        String reviewGreeting = restTemplate.getForObject(reviewServiceUrl + "review", String.class);
        return "composite " + "\n\n" + productGreeting + "\n\n" + recommendationGreeting + "\n\n" + reviewGreeting;
    }
}
