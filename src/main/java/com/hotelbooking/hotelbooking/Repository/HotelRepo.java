package com.hotelbooking.hotelbooking.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hotelbooking.hotelbooking.Entity.Hotel;

public interface HotelRepo extends MongoRepository<Hotel,String> {

    @Query(value = "{'location':?0}")
    Hotel findHotelByLocation(String location);
}
