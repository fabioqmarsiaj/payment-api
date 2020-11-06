package com.fabiomarsiaj.paymentapi.controllers;

import com.fabiomarsiaj.paymentapi.domains.CardRequest;
import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private static final Gson gson = new Gson();

    @PostMapping("/create")
    public ResponseEntity<String> createMethod(
            @RequestParam String apiKey,
            @RequestBody CardRequest cardRequest) throws StripeException {

        Stripe.apiKey = apiKey;

        Map<String, Object> card = new HashMap<>();
        card.put("number", "4242424242424242");
        card.put("exp_month", 11);
        card.put("exp_year", 2021);
        card.put("cvc", "314");
        Map<String, Object> params = new HashMap<>();
        params.put("type", "card");
        params.put("card", card);

        PaymentMethod paymentMethod =
                PaymentMethod.create(params);

        return ResponseEntity.ok(gson.toJson(paymentMethod));
    }

    @GetMapping("/retrieve")
    public ResponseEntity<String> retrieveMethod(
            @RequestParam String secretKey,
            @RequestParam String id) throws StripeException {

        Stripe.apiKey = secretKey;

        PaymentMethod paymentMethod =
                PaymentMethod.retrieve(
                        id
                );

        return ResponseEntity.ok(gson.toJson(paymentMethod));
    }
}
