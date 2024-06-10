package com.hotelbooking.hotelbooking.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<Hotel> searchHotelsByName(String keyword, Pageable pageable) {
        return hotelRepo.findByNameContainingIgnoreCase(keyword, pageable);
    }

    public Page<Hotel> getAllHotels(Pageable pageable) {
        return hotelRepo.findAll(pageable);
    }
    
    
}
