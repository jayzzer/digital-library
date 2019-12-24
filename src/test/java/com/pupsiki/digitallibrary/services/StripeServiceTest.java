package com.pupsiki.digitallibrary.services;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class StripeServiceTest {
    @MockBean
    private StripeService stripeService;

    @Value("${STRIPE_SECRET_KEY}")
    private String API_SECRET_KEY;

    @PostConstruct
    public void init() {
        Stripe.apiKey = API_SECRET_KEY;
    }

    @Test
    public void chargeNewCard() {
        String bookName = "bookname";
        String token = "sometoken";
        Double amount = 1.3;

        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int)(amount * 100));
        chargeParams.put("currency", "RUB");
        chargeParams.put("description", "Покупка книги '" + bookName + "'");
        chargeParams.put("source", token);

//        Charge charge = Charge.create(chargeParams);
//        Assert.assertEquals(charge, stripeService.chargeNewCard(token, amount, bookName));

    }
}