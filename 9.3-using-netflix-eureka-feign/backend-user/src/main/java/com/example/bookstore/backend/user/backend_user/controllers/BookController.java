package com.example.bookstore.backend.user.backend_user.controllers;

import com.example.bookstore.backend.user.backend_user.dto.PriceDto;
import com.example.bookstore.backend.user.backend_user.services.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
@Slf4j
public class BookController {

    private final BookService bookService;

    /*
    ❯ curl http://localhost:8080/user/books/1
    {"book":{"title":"It","author":{"firstName":"Stephen","lastName":"King"}},"price":15.0000}%
     */
    @GetMapping("/{bookId}")
    public ResponseEntity<PriceDto> getOneBook(@PathVariable Long bookId) {
        log.info("Getting book with id {}", bookId);
        return ResponseEntity.ok(bookService.getBookWithPrice(bookId));
    }
}
