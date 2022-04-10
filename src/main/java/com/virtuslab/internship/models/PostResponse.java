package com.virtuslab.internship.models;

import java.math.BigDecimal;
import java.util.List;

public class PostResponse {
    List<List<String>> entries;
    List<String> discounts;
    BigDecimal totalPrice;

    public List<String> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<String> discounts) {
        this.discounts = discounts;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<List<String>> getEntries() {
        return entries;
    }

    public void setEntries(List<List<String>> entries) {
        this.entries = entries;
    }
}
