package com.hotelbooking.hotelbooking.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelbooking.hotelbooking.Entity.Room;
import com.hotelbooking.hotelbooking.Repository.RoomRepo;

@Service
public class RoomService {
    @Autowired
    private RoomRepo roomRepo;

    public List<Room> getRoomsByHotelId(String hotelId) {
        return roomRepo.findRoomsByHotelId(hotelId);
    }

    public List<Room> getRoomsByType(String type) {
        return roomRepo.findRoomsByRoomType(type);
    }

    public Room getRoomById(String roomId) {
        return roomRepo.findById(roomId).orElse(null);
    }

    public void createRoom(Room room) {
        roomRepo.save(room);
    }

    public void updateRoom(Room room) {
        roomRepo.save(room);
    }
}