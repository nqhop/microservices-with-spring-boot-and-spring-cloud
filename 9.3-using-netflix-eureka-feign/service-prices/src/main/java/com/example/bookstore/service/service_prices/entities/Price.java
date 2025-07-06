package com.example.bookstore.service.service_prices.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(
        name = "price",
        schema = "service_prices"
)
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_price_generator")
    private Long id;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(nullable = false, precision = 10, scale = 4)
    private BigDecimal price;
}
