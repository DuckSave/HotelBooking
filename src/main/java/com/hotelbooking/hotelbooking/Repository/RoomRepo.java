package com.hotelbooking.hotelbooking.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hotelbooking.hotelbooking.Entity.Room;

public interface RoomRepo extends MongoRepository<Room,String>{

} 