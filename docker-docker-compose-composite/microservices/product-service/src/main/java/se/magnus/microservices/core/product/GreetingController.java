package se.magnus.microservices.core.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/product")
    public String index(){
        return "Greetings from product service!";
    }
}
