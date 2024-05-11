package com.hotelbooking.hotelbooking.Repository;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hotelbooking.hotelbooking.Entity.Account;

public interface AccountRepo extends MongoRepository<Account,Integer> {

    Map<String,String> otpStorage = new HashMap<>();

    @Query(value = "{'phoneNumber':?0}")
    Account findAccountByPhoneNumber(String phoneNumber);

    public static String generateOtp(String key){
        if (otpStorage.containsKey(key)) {
            otpStorage.remove(key);
        }
        String otp = String.format("%04d", new Random().nextInt(10000));
        otpStorage.put(key, otp);
        return otp;
    }

    public static  boolean validateOtp(String key, String otp){
        if (otpStorage.containsKey(key) && otpStorage.containsValue(otp)) {
            otpStorage.remove(key);
            return true;
        }
        return false;
    }

} 
