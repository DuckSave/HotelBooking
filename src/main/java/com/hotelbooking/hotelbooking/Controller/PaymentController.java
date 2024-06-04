package com.hotelbooking.hotelbooking.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hotelbooking.hotelbooking.Service.PaymentService;

@Controller
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create_payment")
    public ResponseEntity<?> createPayment(@RequestBody Map<String, Object> payload) {
        long amount = ((Number) payload.get("totalPrice")).longValue();
        String paymentURL = paymentService.getPaymentUrl(amount);
        return null;
    }
}
