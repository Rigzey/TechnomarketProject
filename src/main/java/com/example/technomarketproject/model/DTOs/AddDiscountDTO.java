package com.example.technomarketproject.model.DTOs;

import jakarta.validation.constraints.*;
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

    @PastOrPresent(message = "Invalid start date")
    protected LocalDate dateFrom;

    @Future(message = "Invalid end date")
    protected LocalDate dateTo;

    @Max(value = 100, message = "Discount percentage cannot be more than 100%")
    @Min(value = 1, message = "Discount percantage cannot be less than 1%")
    protected int percentageOff;

    protected ProductWithIdOnlyDTO product;
}
