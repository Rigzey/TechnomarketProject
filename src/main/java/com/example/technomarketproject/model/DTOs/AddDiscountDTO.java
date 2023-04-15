package com.example.technomarketproject.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddDiscountDTO {
    protected LocalDate dateTo;
    protected LocalDate dateFrom;
    protected int percentageOff;
    protected ProductWithIdOnlyDTO product;
}
