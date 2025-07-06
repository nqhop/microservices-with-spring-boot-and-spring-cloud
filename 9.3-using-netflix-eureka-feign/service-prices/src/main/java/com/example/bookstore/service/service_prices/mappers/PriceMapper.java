package com.example.bookstore.service.service_prices.mappers;

import com.example.bookstore.service.service_prices.dto.PriceDto;
import com.example.bookstore.service.service_prices.entities.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    PriceDto toPriceDto(Price price);
}
