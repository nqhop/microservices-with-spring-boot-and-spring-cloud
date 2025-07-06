package com.example.bookstore.service.books.mapper;

import com.example.bookstore.service.books.dto.AuthorDto;
import com.example.bookstore.service.books.entities.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDto toAuthorDto(Author author);
}
