package com.example.bookstore.service.books.services;

import com.example.bookstore.service.books.dto.BookDto;
import com.example.bookstore.service.books.exceptions.AppException;
import com.example.bookstore.service.books.mapper.BookMapper;
import com.example.bookstore.service.books.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    public BookDto getBook(long bookId) {
        BookDto bookDto = bookRepository.findById(bookId)
                .map(bookMapper::toBookDto)
                .orElseThrow(() -> new AppException("No book found with Id " + bookId, HttpStatus.NOT_FOUND));
        return bookDto;
    }

    public List<BookDto> getAllBooks() {
        List<BookDto> bookDtoList =  bookRepository.findAll()
                .stream().map(bookMapper::toBookDto).toList();
        return bookDtoList;
    }
}
