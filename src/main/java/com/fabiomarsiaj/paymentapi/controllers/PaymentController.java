package com.fabiomarsiaj.paymentapi.controllers;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Value("${STRIPE_PUBLIC_KEY}")
    private String apiKey;
    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    private static final Gson gson = new Gson();

    @PostMapping("/create")
    public ResponseEntity<String> create(
            @RequestParam int amount,
            @RequestParam String currency
    ) throws StripeException {
        Stripe.apiKey = secretKey;

        List<Object> paymentMethodTypes =
                new ArrayList<>();
        paymentMethodTypes.add("card");
        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", currency);
        params.put("payment_method_types", paymentMethodTypes);

        PaymentIntent paymentIntent =
                PaymentIntent.create(params);

        return ResponseEntity.ok(gson.toJson(paymentIntent));
    }
}
