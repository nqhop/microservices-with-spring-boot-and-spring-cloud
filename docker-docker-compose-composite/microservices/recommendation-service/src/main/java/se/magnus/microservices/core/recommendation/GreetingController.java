package se.magnus.microservices.core.recommendation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/recommendation")
    public String index(){
        return "Greetings from recommendation service!";
    }
}
