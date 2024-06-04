package com.hotelbooking.hotelbooking.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hotelbooking.hotelbooking.Entity.HotelBooking;

public interface HotelBookingRepo extends MongoRepository<HotelBooking,String>{
    
}
