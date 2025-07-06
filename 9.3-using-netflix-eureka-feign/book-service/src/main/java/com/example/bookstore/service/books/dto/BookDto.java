package com.example.bookstore.service.books.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private String title;
    private AuthorDto author;
    private BigDecimal price;
}
