package com.example.technomarketproject.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleOrderDTO {
    private int id;
    private LocalDate orderDate;
    private String deliveryAddress;
    private double totalPrice;
    private UserWithoutPasswordDTO user;
    private List<SimpleProductDTO> products;
}
