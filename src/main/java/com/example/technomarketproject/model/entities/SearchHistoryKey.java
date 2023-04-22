package com.example.technomarketproject.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SearchHistoryKey implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "product_id")
    private int productId;


}
