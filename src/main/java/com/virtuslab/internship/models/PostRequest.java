package com.virtuslab.internship.models;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;

import java.util.ArrayList;
import java.util.List;

public class PostRequest {
    List<String> productsNames;

    public List<String> getProductsNames() {
        return productsNames;
    }

    public Basket setBasket() {
        Basket basket = new Basket();
        ProductDb productDb = new ProductDb();
        for (String productsName : productsNames) {
            Product product = productDb.getProduct(productsName);
            basket.addProduct(product);
        }
        return basket;
    }

    public List<List<String>> setEntries(Receipt receipt) {
        List<List<String>> entries = new ArrayList<>();
        for(int i = 0; i < receipt.entries().size(); i++) {
            List<String> entry = new ArrayList<>();
            entry.add(receipt.entries().get(i).product().name());
            entry.add(Integer.toString(receipt.entries().get(i).quantity()));
            entry.add((receipt.entries().get(i).totalPrice()).toString());
            entries.add(entry);
        }
        return entries;
    }
}
