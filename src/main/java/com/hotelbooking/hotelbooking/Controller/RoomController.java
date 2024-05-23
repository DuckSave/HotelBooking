package com.hotelbooking.hotelbooking.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hotelbooking.hotelbooking.Entity.Room;
import com.hotelbooking.hotelbooking.Repository.RoomRepo;
import com.hotelbooking.hotelbooking.Utils.FileStorageUtil;

@Controller
public class RoomController {

    private static final String ASSETS_IMAGES_DIR = "src/main/resources/static/assets/images/";

    @Autowired
    RoomRepo roomRepo;

    @PostMapping("/addRoom")
    public String addRoom(@RequestParam("roomId") String id,
                        @RequestParam("roomType") String roomType,
                        @RequestParam("price") int price,
                        @RequestParam("images") List<MultipartFile> images) {

        List<String> imagePaths = new ArrayList<>();
        for (MultipartFile image : images) {
            String imagePath = FileStorageUtil.saveFile(image, ASSETS_IMAGES_DIR);
            if (imagePath != null) {
                imagePaths.add(imagePath);
            }
        }

        Room exitsRoom = roomRepo.findById(id).orElse(null);

        
        return "addRoom";
    }
}
