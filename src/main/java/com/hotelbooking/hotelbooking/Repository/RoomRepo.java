package com.hotelbooking.hotelbooking.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.hotelbooking.hotelbooking.Entity.Room;

public interface RoomRepo extends MongoRepository<Room, String> {

    @Query(value = "{'hotelId':?0}")
    List<Room> findRoomsByHotelId(String hotelId);

    @Query(value = "{'roomType':?0}")
    List<Room> findRoomsByRoomType(String roomType);

}