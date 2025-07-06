package com.example.bookstore.backend.user.backend_user.feign;

import com.example.bookstore.backend.user.backend_user.dto.BookDto;
import com.example.bookstore.backend.user.backend_user.dto.PriceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("service-books")
public interface ServiceBooks {

    @RequestMapping("/books/{bookId}")
    BookDto getBook(@PathVariable("bookId") long bookId);
}
