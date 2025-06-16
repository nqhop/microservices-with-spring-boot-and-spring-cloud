package java.se.magnus.api.composite;

import org.springframework.web.bind.annotation.GetMapping;

public interface ProductCompositeService {

    @GetMapping("/product-composite")
    String getProduct();
}
