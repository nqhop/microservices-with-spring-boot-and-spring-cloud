package com.example.bookstore.service.service_prices.controllers;

import com.example.bookstore.service.service_prices.dto.PriceDto;
import com.example.bookstore.service.service_prices.services.PriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class PricesController {

    private final PriceService priceService;

    @GetMapping("/books/{bookId}")
    public ResponseEntity<PriceDto> getPrice(@PathVariable Long bookId) {
        log.info("Get price for book id {}", bookId);
        return ResponseEntity.ok(priceService.getPrice(bookId));
    }
}
