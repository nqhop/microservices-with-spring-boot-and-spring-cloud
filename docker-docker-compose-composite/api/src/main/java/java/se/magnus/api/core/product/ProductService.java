package java.se.magnus.api.core.product;

import org.springframework.web.bind.annotation.GetMapping;

public interface ProductService {

    @GetMapping("/product")
    String getProduct();
}
