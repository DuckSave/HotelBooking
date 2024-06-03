package com.hotelbooking.hotelbooking.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelbooking.hotelbooking.Entity.Hotel;
import com.hotelbooking.hotelbooking.Repository.HotelRepo;

@Service
public class HotelService {
    @Autowired
    private HotelRepo hotelRepo;

    public Hotel getHotelById(String hotelId) {
        Hotel hotel = hotelRepo.findById(hotelId).get();
        return hotel == null ? null : hotel;
    }
    
}
