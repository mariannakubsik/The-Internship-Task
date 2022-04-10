package com.virtuslab.internship.discount;

import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;

import java.math.BigDecimal;
import java.util.List;

import static com.virtuslab.internship.product.Product.Type.GRAINS;

abstract public class Discount {

    public String name;
    public double discount;

    public Discount(final String name, final double discount) {
        this.name = name;
        this.discount = discount;
    }

    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(1 - this.discount));
            var discounts = receipt.discounts();
            discounts.add(this.name);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    private boolean shouldApply(Receipt receipt) {
        return switch (name) {
            case "TenPercentDiscount" -> receipt.totalPrice().compareTo(BigDecimal.valueOf(50)) > 0;
            case "FifteenPercentDiscount" -> countGrainProducts(receipt.entries()) >= 3;
            default -> false;
        };
    }

    public int countGrainProducts(List<ReceiptEntry> receiptEntries) {
        return receiptEntries.stream()
                .filter(product -> GRAINS.equals(product.product().type()))
                .mapToInt(ReceiptEntry::quantity)
                .sum();
    }

}