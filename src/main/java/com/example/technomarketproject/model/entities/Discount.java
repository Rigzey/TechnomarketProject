package com.example.technomarketproject.model.entities;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "discounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate dateTo;

    private LocalDate dateFrom;

    private int percentageOff;

    private boolean isActive;
}
