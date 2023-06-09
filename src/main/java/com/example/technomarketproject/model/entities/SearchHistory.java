package com.example.technomarketproject.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "search_history")
public class SearchHistory {
    @EmbeddedId
    private SearchHistoryKey id;

    @OneToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product productId;

    private LocalDateTime lastSeen;

}
