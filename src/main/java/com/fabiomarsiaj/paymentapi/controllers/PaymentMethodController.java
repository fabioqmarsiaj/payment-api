package com.fabiomarsiaj.paymentapi.controllers;

import com.fabiomarsiaj.paymentapi.domains.CardRequest;
import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paymentmethod")
public class PaymentMethodController {

    @Value("${STRIPE_PUBLIC_KEY}")
    private String apiKey;
    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    private static final Gson gson = new Gson();

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody CardRequest cardRequest) throws StripeException {
        Stripe.apiKey = apiKey;

        Map<String, Object> card = new HashMap<>();
        card.put("number", cardRequest.getNumber());
        card.put("exp_month", cardRequest.getExpMonth());
        card.put("exp_year", cardRequest.getExpYear());
        card.put("cvc", cardRequest.getCvc());
        Map<String, Object> params = new HashMap<>();
        params.put("type", "card");
        params.put("card", card);

        PaymentMethod paymentMethod =
                PaymentMethod.create(params);

        return ResponseEntity.ok(gson.toJson(paymentMethod));
    }

    @GetMapping("/retrieve")
    public ResponseEntity<String> retrieve(@RequestParam String id) throws StripeException {
        Stripe.apiKey = secretKey;

        PaymentMethod paymentMethod =
                PaymentMethod.retrieve(
                        id
                );

        return ResponseEntity.ok(gson.toJson(paymentMethod));
    }

    @PostMapping("/attach")
    public ResponseEntity<String> attach(@RequestParam String id) throws StripeException {
        Stripe.apiKey = secretKey;

        PaymentMethod paymentMethod =
                PaymentMethod.retrieve(
                        id
                );

        Map<String, Object> params = new HashMap<>();
        params.put("customer", "cus_6rCU6GJt5WaMT6");

        PaymentMethod updatedPaymentMethod =
                paymentMethod.attach(params);

        return ResponseEntity.ok(gson.toJson(updatedPaymentMethod));
    }
}
