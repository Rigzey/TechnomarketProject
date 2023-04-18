package com.example.technomarketproject.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SearchHistoryKey implements Serializable {
    @Column(name = "user_id")
    private int userid;

    @Column(name = "product_id")
    private int productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchHistoryKey that = (SearchHistoryKey) o;
        return userid == that.userid && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, productId);
    }
}
