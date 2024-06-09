package com.hotelbooking.hotelbooking.Service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class GenerateCode {

    private static final String DIGITS = "0123456789";
    private static final int CODE_LENGTH = 8; // độ dài của mã ngẫu nhiên
    private static final SecureRandom RANDOM = new SecureRandom();

    public String generateCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = RANDOM.nextInt(DIGITS.length());
            code.append(DIGITS.charAt(randomIndex));
        }

        return code.toString();
    }
    
}
