package com.fabiomarsiaj.paymentapi.controllers;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private static final Gson gson = new Gson();

    @Value("${STRIPE_PUBLIC_KEY}")
    private String apiKey;
    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    @PostMapping("/create")
    public ResponseEntity<String> create(
            @RequestParam String name,
            @RequestParam String email) throws StripeException {
        Stripe.apiKey = secretKey;

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("description", "My First Test Customer (created for API docs)");

        Customer customer = Customer.create(params);

        return ResponseEntity.ok(gson.toJson(customer));
    }

    @GetMapping("/retrieve")
    public ResponseEntity<String> get(@RequestParam String id) throws StripeException {
        Stripe.apiKey = secretKey;

        Customer customer =
                Customer.retrieve(id);

        return ResponseEntity.ok(gson.toJson(customer));
    }
}
