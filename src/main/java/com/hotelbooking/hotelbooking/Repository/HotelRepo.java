package com.hotelbooking.hotelbooking.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hotelbooking.hotelbooking.Entity.Hotel;

public interface HotelRepo extends MongoRepository<Hotel,String> {

    @Query(value = "{'address':?0}")
    Hotel findHotelByAddress(String address);

    @Query(value = "{'location':?0}")
    List<Hotel> findListHotelByLocation(String location);
}
