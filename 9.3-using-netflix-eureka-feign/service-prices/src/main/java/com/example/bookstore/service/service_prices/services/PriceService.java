package com.example.bookstore.service.service_prices.services;

import com.example.bookstore.service.service_prices.dto.PriceDto;
import com.example.bookstore.service.service_prices.mappers.PriceMapper;
import com.example.bookstore.service.service_prices.respositories.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PriceService {

    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    public PriceDto getPrice(long bookId){
     return priceRepository.findById(bookId).map(priceMapper::toPriceDto).orElse(null);
    }
}
