package com.example.bookstore.service.service_prices.respositories;

import com.example.bookstore.service.service_prices.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    Optional<Price> findByBookId(Long bookId);
}
