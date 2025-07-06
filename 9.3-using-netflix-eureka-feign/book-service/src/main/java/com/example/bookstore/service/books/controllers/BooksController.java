package com.example.bookstore.service.books.controllers;

import com.example.bookstore.service.books.dto.BookDto;
import com.example.bookstore.service.books.services.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BooksController {

    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> getAllBooks(){
        log.info("getAllBooks");
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookDto> getOneBook(@PathVariable long bookId){
        log.info("getOneBook {}", bookId);
        return ResponseEntity.ok(bookService.getBook(bookId));
    }


}
