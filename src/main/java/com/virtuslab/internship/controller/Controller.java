package com.virtuslab.internship.controller;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.models.PostRequest;
import com.virtuslab.internship.models.PostResponse;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public PostResponse Test(@RequestBody PostRequest inputPayload) {
        PostResponse response = new PostResponse();
        Basket basket = inputPayload.setBasket();
        ReceiptGenerator receiptGenerator = new ReceiptGenerator();
        Receipt receipt = receiptGenerator.generate(basket);
        response.setEntries(inputPayload.setEntries(receipt));
        response.setDiscounts(receipt.discounts());
        response.setTotalPrice(receipt.totalPrice());
        return response;
    }
}
