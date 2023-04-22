package com.example.technomarketproject.model.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddOrderDTO {
    @PastOrPresent(message = "Invalid order date")
    private LocalDate orderDate;
    @Size(min = 1, max = 200, message = "Invalid delivery address size")
    private String deliveryAddress;
    @Min(value = 0, message = "Total price cannot be negative")
    private double totalPrice;
}
