package se.magnus.microservices.core.review;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/review")
    public String index(){
        return "Greetings from review service!";
    }
}
