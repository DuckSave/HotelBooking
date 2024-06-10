package com.hotelbooking.hotelbooking.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotelbooking.hotelbooking.Service.BookingHotelService;
import com.hotelbooking.hotelbooking.Service.EmailSenderService;
import com.hotelbooking.hotelbooking.Service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BookingHotelService hotelBookingService;

    @Autowired
    private EmailSenderService mailService;

    @PostMapping("/create_payment")
    public ResponseEntity<?> createPayment(@RequestBody Map<String, Object> payload,HttpServletRequest req) {
        String paymentURL = paymentService.getPaymentUrl(payload,req);

        if(paymentURL == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("status","PAYMENT_FAIL"));
        }

        return ResponseEntity.ok().body(Map.of("status","PAYMENT_SUCCESS", "paymentURL", paymentURL));
    }

    @GetMapping("/info")
    public String getInfo(
        @RequestParam("vnp_TxnRef") String vnp_vnp_TxnRef,
        @RequestParam("vnp_Amount") String amount,
        @RequestParam("vnp_BankCode") String bankCode,
        @RequestParam("vnp_OrderInfo") String order,
        @RequestParam("vnp_ResponseCode") String responseCode){
        System.out.println("return");
        if (responseCode.equals("00")) {
            hotelBookingService.setStatusPayment(vnp_vnp_TxnRef);
            mailService.sendEmailToBookingPerson(vnp_vnp_TxnRef);
            return "redirect:/index";
        } 

        return "redirect:/index";
    }
}
