package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = generateReceiptEntries(basket);
        List<String> discounts = new ArrayList<>();
        Receipt receipt = new Receipt(receiptEntries, discounts);
        return generateDiscounts(receipt);
    }

    public Receipt generateDiscounts(Receipt receipt) {
        FifteenPercentDiscount fifteenPercentDiscount = new FifteenPercentDiscount();
        Receipt receiptAfterFifteenPercentDiscount = fifteenPercentDiscount.apply(receipt);
        TenPercentDiscount tenPercentDiscount = new TenPercentDiscount();
        return tenPercentDiscount.apply(receiptAfterFifteenPercentDiscount);
    }

    public List<ReceiptEntry> generateReceiptEntries(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        for(Map.Entry<String, Long> entry: getProductsWithQuantity(basketToNamesList(basket))) {
            var productDb = new ProductDb();
            var product = productDb.getProduct(entry.getKey());
            var quantity = Math.toIntExact(entry.getValue());
            receiptEntries.add(new ReceiptEntry(product, quantity));
        }
        return receiptEntries;
    }

    public List<String> basketToNamesList(Basket basket) {
        List<String> names = new ArrayList<>();
        for (Product product: basket.getProducts()) {
            names.add(product.name());
        }
        return names;
    }

    public Set<Map.Entry<String, Long>> getProductsWithQuantity(List<String> names) {
        Map<String, Long> frequencyMap = names.stream()
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()));
        return frequencyMap.entrySet();
    }

}
