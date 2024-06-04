package com.hotelbooking.hotelbooking.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hotelbooking.hotelbooking.Entity.Hotel;
import com.hotelbooking.hotelbooking.Entity.Room;
import com.hotelbooking.hotelbooking.Repository.RoomRepo;
import com.hotelbooking.hotelbooking.Service.RoomService;
import com.hotelbooking.hotelbooking.Utils.FileStorageUtil;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomController {

    private static final String ASSETS_IMAGES_DIR = "src/main/resources/static/assets/images/";

    @Autowired
    RoomService roomService;

    @PostMapping("/rooms/add")
    public ResponseEntity<Map<String, String>> addRoom(@RequestParam("RoomNumber") String id,
            @RequestParam("HotelName") String hotelId,
            @RequestParam("RoomName") String roomType,
            @RequestParam("price") int price,
            @RequestParam("images") List<MultipartFile> images,
            @RequestParam("description") String description){

        List<String> imagePaths = new ArrayList<>();
        for (MultipartFile image : images) {
            String imagePath = FileStorageUtil.saveFile(image, ASSETS_IMAGES_DIR);
            if (imagePath != null) {
                imagePaths.add(imagePath);
            }
        }

        Room exitsRoom = roomService.getRoomById(id);
        if (exitsRoom == null) {
            Room room = new Room();
            room.setId(id);
            room.setRoomType(roomType);
            room.setPrice(price);
            room.setImages(imagePaths);
            room.setHotelId(hotelId);
            room.setDescription(description);
            roomService.createRoom(room);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "ADD_ROOM_SUCCESS"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("status", "ADD_ROOM_FAILED", "message", "room already exists"));
    }
}
