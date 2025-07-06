package com.example.bookstore.backend.user.backend_user.services;

import com.example.bookstore.backend.user.backend_user.dto.BookDto;
import com.example.bookstore.backend.user.backend_user.dto.PriceDto;
import com.example.bookstore.backend.user.backend_user.feign.ServiceBooks;
import com.example.bookstore.backend.user.backend_user.feign.ServicePrices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final ServiceBooks serviceBooks;
    private final ServicePrices servicePrices;

    public PriceDto getBookWithPrice(Long bookId) {

        BookDto book = serviceBooks.getBook(bookId);

        PriceDto price = servicePrices.getPrice(bookId);

        price.setBook(book);

        return price;
    }
}
