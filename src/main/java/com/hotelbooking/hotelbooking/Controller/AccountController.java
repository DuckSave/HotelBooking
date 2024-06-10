package com.hotelbooking.hotelbooking.Controller;

import org.springframework.web.bind.annotation.RestController;
import com.hotelbooking.hotelbooking.Entity.Account;
import com.hotelbooking.hotelbooking.Repository.AccountRepo;
import com.hotelbooking.hotelbooking.Service.AccountService;
import com.hotelbooking.hotelbooking.Service.GenerateCode;
import com.hotelbooking.hotelbooking.Service.SessionService;
import com.hotelbooking.hotelbooking.Utils.Encode;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AccountController {

    @Autowired
    AccountRepo accountRepository;

    @Autowired
    GenerateCode generateCode;

    @Autowired
    SessionService sessionService;

    @Autowired
    AccountService accountService;

    @PostMapping("/login")
    public String login(@ModelAttribute Account account, HttpSession session) {
        System.out.println("login");
        Account existingAccount = accountRepository.findAccountByPhoneNumber(account.getPhoneNumber());
        String EncodePassword = Encode.encode(account.getPassword());
        if(existingAccount != null ){
            if (existingAccount.getPassword().equals(EncodePassword)) {
                sessionService.setSession("account", existingAccount, session);
                return "redirect:/index";
            } else {
                return "redirect:/login";
            }
        }

        return "redirect:/login";

    }

    @PostMapping("/addAccount")
    public ResponseEntity<?> addAccount(@RequestBody Map<String, String> payload) {
        Account newAccount = new Account();
        newAccount.setFirstName(payload.get("firstName"));
        newAccount.setLastName(payload.get("lastName"));
        newAccount.setPhoneNumber(payload.get("phoneNumber"));
        newAccount.setPassword(Encode.encode(payload.get("password")));
        System.out.println(newAccount.toString());
        newAccount.setRole(false);
        newAccount.setBookingId(generateCode.generateCode());
        accountRepository.save(newAccount);
        return ResponseEntity.ok(Map.of("status", "ACCOUNT_CREATED"));
    }

    @PostMapping("/updateAccount")
    public String updateAccount(@RequestBody Account account) {

        Account exitsAccount = accountRepository.findAccountByPhoneNumber(account.getPhoneNumber());
        if (exitsAccount != null) {
            exitsAccount.setFirstName(account.getFirstName());
            exitsAccount.setLastName(account.getLastName());
            exitsAccount.setPhoneNumber(account.getPhoneNumber());
            exitsAccount.setPassword(account.getPassword());
            accountRepository.save(exitsAccount);
        } else {
            System.out.println("account not found");
        }

        return "updateAccount success";
    }

    @PostMapping("/check-phoneNumber")
    public ResponseEntity<?> checkPhoneNumber(@RequestBody Map<String, String> payload) {
        System.out.println("checkPhoneNumber");

        String phoneNumber = payload.get("phoneNumber");
        Account exitsAccount = accountRepository.findAccountByPhoneNumber(phoneNumber);
        if (exitsAccount == null) {
            String otp = AccountRepo.generateOtp(phoneNumber);
            System.out.println(otp);
            return ResponseEntity.ok(Map.of("status", "OTP_REQUIRED", "message", otp));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Phone number already registered", "status", "ALREADY_REGISTERED"));
    }

    @PostMapping("/validateOtp")
    public ResponseEntity<?> validateOtp(@RequestBody Map<String, String> payload) {
        String phoneNumber = payload.get("phoneNumber");
        String otp = payload.get("otp");
        boolean isValid = AccountRepo.validateOtp(phoneNumber, otp);
        if (isValid) {
            return ResponseEntity.ok(Map.of("status", "OTP_VALID"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Invalid OTP", "status", "OTP_INVALID"));
    }

    @PostMapping("/generateOtp")
    public ResponseEntity<?> generateOtp(@RequestBody Map<String, String> payload) {
        String phoneNumber = payload.get("phoneNumber");
        String otp = AccountRepo.generateOtp(phoneNumber);
        return ResponseEntity.ok(Map.of("status", "OTP_REQUIRED", "message", otp));
    }

    @PostMapping("/testPhoneNumber")
    public ResponseEntity<?> testPhoneNumber(@RequestBody Map<String, String> payload) {
        String phoneNumber = payload.get("phoneNumber");
        Account exitsAccount = accountRepository.findAccountByPhoneNumber(phoneNumber);

        if (exitsAccount == null) {
            return ResponseEntity.ok(Map.of("status", "yes", "message", "dang ky thanh cong"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "dang ky that bai"));
    }

}
