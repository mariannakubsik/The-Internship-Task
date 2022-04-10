package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.product.ProductDb;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptGeneratorTest {

    @Test
    void shouldGenerateReceiptForGivenBasket() {
        // Given
        var productDb = new ProductDb();
        var cart = new Basket();
        var milk = productDb.getProduct("Milk");
        var bread = productDb.getProduct("Bread");
        var apple = productDb.getProduct("Apple");
        var expectedTotalPrice = milk.price().multiply(BigDecimal.valueOf(2)).add(bread.price()).add(apple.price());

        cart.addProduct(milk);
        cart.addProduct(milk);
        cart.addProduct(bread);
        cart.addProduct(apple);

        var receiptGenerator = new ReceiptGenerator();

        // When
        var receipt = receiptGenerator.generate(cart);

        // Then
        assertNotNull(receipt);
        assertEquals(3, receipt.entries().size());
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(0, receipt.discounts().size());
    }

    @Test
    void shouldGenerateReceiptWith10PercentDiscountForGivenBasket() {
        // Given
        var productDb = new ProductDb();
        var cart = new Basket();
        var milk = productDb.getProduct("Milk");
        var bread = productDb.getProduct("Bread");
        var steak = productDb.getProduct("Steak");
        var expectedTotalPrice = milk.price().multiply(BigDecimal.valueOf(2)).add(bread.price()).add(steak.price()).multiply(BigDecimal.valueOf(0.9));

        cart.addProduct(milk);
        cart.addProduct(milk);
        cart.addProduct(bread);
        cart.addProduct(steak);

        var receiptGenerator = new ReceiptGenerator();

        // When
        var receipt = receiptGenerator.generate(cart);

        // Then
        assertNotNull(receipt);
        assertEquals(3, receipt.entries().size());
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(1, receipt.discounts().size());
    }

    @Test
    void shouldGenerateReceiptWith15PercentDiscountForGivenBasket() {
        // Given
        var productDb = new ProductDb();
        var cart = new Basket();
        var milk = productDb.getProduct("Milk");
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        var expectedTotalPrice = milk.price().add(bread.price()).add((cereals.price()).multiply(BigDecimal.valueOf(2))).multiply(BigDecimal.valueOf(0.85));

        cart.addProduct(cereals);
        cart.addProduct(cereals);
        cart.addProduct(bread);
        cart.addProduct(milk);

        var receiptGenerator = new ReceiptGenerator();

        // When
        var receipt = receiptGenerator.generate(cart);

        // Then
        assertNotNull(receipt);
        assertEquals(3, receipt.entries().size());
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(1, receipt.discounts().size());
    }

    @Test
    void shouldGenerateReceiptWithDiscountsForGivenBasket() {
        // Given
        var productDb = new ProductDb();
        var cart = new Basket();
        var potato = productDb.getProduct("Potato");
        var bread = productDb.getProduct("Bread");
        var apple = productDb.getProduct("Apple");
        var cereals = productDb.getProduct("Cereals");
        var steak = productDb.getProduct("Steak");
        var expectedTotalPrice = ((potato.price().multiply(BigDecimal.valueOf(2)).add(bread.price().multiply(BigDecimal.valueOf(2))).add(apple.price()).add(cereals.price().multiply(BigDecimal.valueOf(2))).add(steak.price())) .multiply(BigDecimal.valueOf(0.85))).multiply(BigDecimal.valueOf(0.9));

        cart.addProduct(potato);
        cart.addProduct(potato);
        cart.addProduct(bread);
        cart.addProduct(bread);
        cart.addProduct(apple);
        cart.addProduct(cereals);
        cart.addProduct(cereals);
        cart.addProduct(steak);

        var receiptGenerator = new ReceiptGenerator();

        // When
        var receipt = receiptGenerator.generate(cart);

        // Then
        assertNotNull(receipt);
        assertEquals(5, receipt.entries().size());
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(2, receipt.discounts().size());
    }

}
