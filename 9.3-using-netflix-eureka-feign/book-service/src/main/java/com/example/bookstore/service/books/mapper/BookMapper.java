package com.example.bookstore.service.books.mapper;

import com.example.bookstore.service.books.dto.BookDto;
import com.example.bookstore.service.books.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
uses = {AuthorMapper.class})
public interface BookMapper {

    @Mapping(target = "price", ignore = true)
    BookDto toBookDto(Book book);
}
