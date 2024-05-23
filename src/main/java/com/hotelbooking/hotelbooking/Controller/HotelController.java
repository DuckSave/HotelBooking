package com.hotelbooking.hotelbooking.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hotelbooking.hotelbooking.Entity.Hotel;
import com.hotelbooking.hotelbooking.Repository.HotelRepo;
import com.hotelbooking.hotelbooking.Utils.FileStorageUtil;


@Controller
public class HotelController {

    // Using classpath to dynamically get the resource directory
    private static final String ASSETS_IMAGES_DIR = "src/main/resources/static/assets/images/";

    @Autowired
    HotelRepo hotelRepository;


    @PostMapping("/addHotel")
    public ResponseEntity<?> addHotel(
            @RequestParam("hotelName") String hotelName,
            @RequestParam("location") String location,
            @RequestParam("star") int star,
            @RequestParam("price") int price,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image) {


            Hotel existHotel = hotelRepository.findHotelByLocation(location);

            // Save the image
            String imageFileName = FileStorageUtil.saveFile(image, ASSETS_IMAGES_DIR);
            if (imageFileName == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("status", "ADD_HOTEL_FAILED", "message", "Image upload failed"));
            }

            if (existHotel == null) {
                Hotel newHotel = new Hotel();
                newHotel.setHotelName(hotelName);
                newHotel.setLocation(location);
                newHotel.setStar(star);
                newHotel.setPrice(price);
                newHotel.setDescription(description);
                newHotel.setImage(imageFileName); 
                hotelRepository.save(newHotel);

                return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "ADD_HOTEL_SUCCESS"));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "ADD_HOTEL_FAILED", "message", "Hotel already exists"));
 
    }

}
