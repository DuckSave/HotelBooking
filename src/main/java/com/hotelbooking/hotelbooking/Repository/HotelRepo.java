package com.hotelbooking.hotelbooking.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hotelbooking.hotelbooking.Entity.Hotel;

public interface HotelRepo extends MongoRepository<Hotel,String> {

    @Query(value = "{'address': {$regex: ?0, $options: 'i'}}")
    Hotel findHotelByAddress(String address);

    @Query(value = "{'location': {$regex: ?0, $options: 'i'}}")
    List<Hotel> findListHotelByLocation(String location);

    @Query(value = "{'location': {$regex: ?0, $options: 'i'}, 'address': {$regex: ?1, $options: 'i'}}")
    Hotel findHotelByLocationAndAddress(String location, String address);

    List<Hotel> findByPriceBetween(int priceFrom, int priceTo );

    List<Hotel> findByStarIn(List<Integer> stars);
}
