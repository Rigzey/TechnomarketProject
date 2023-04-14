package com.example.technomarketproject.model.entities;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "date_to", nullable = false)
    private LocalDate dateTo;

    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;

    @Column(name = "percentage_off", nullable = false)
    private int percentageOff;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "discount")
    List<Product> products;

}
