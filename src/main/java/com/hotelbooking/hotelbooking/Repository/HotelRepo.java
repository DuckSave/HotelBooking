package com.hotelbooking.hotelbooking.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hotelbooking.hotelbooking.Entity.Hotel;

public interface HotelRepo extends MongoRepository<Hotel,String> {

    @Query(value = "{'address':?0}")
    Hotel findHotelByAddress(String address);

    @Query(value = "{'location':?0}")
    List<Hotel> findListHotelByLocation(String location);

    @Query(value = "{ 'hotelName' : { $regex: ?0, $options: 'i' } }")
    Page<Hotel> findByNameContainingIgnoreCase(String hotelName, Pageable pageable);

    @Query(value = "{'location': ?0, 'address': ?1}")
    Hotel findHotelByLocationAndAddress(String location, String address);

    List<Hotel> findByPriceBetween(int priceFrom, int priceTo );

    List<Hotel> findByStarIn(List<Integer> stars);

}
