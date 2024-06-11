package com.hotelbooking.hotelbooking.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hotelbooking.hotelbooking.Entity.Hotel;
import com.hotelbooking.hotelbooking.Repository.HotelRepo;
import com.hotelbooking.hotelbooking.Service.HotelService;
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
            @RequestParam("address") String address,
            @RequestParam("star") int star,
            @RequestParam("price") int price,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image) {

        Hotel existHotel = hotelRepository.findHotelByAddress(address);

        // Save the image
        String imageFileName = FileStorageUtil.saveFile(image, ASSETS_IMAGES_DIR);
        if (imageFileName == null) {
            System.out.println("asdasd");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("status", "ADD_HOTEL_FAILED", "message", "Image upload failed"));
        }

        if (existHotel == null) {
            Hotel newHotel = new Hotel();
            newHotel.setHotelName(hotelName);
            newHotel.setLocation(location);
            newHotel.setAddress(address);
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


    @PostMapping("/deleteHotel")
    public ResponseEntity<?> deleteHotel(@RequestBody Map<String, String> payload, Model model ) {
        String id = payload.get("idHotel");
        Optional<Hotel> existHotel = hotelRepository.findById(id);
        if (existHotel.isPresent()) {
            hotelRepository.delete(existHotel.get());
            return ResponseEntity.ok().body(Map.of("status", "Delete_Success"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("status", "Delete_Unsuccess"));
        
    }
    
    @PostMapping("/editHotel")
    public ResponseEntity<?> editHotel(@RequestBody Map<String, String> payload) {
        
        String id = payload.get("idHotel");
        System.out.println(id);
        Hotel existHotel = hotelRepository.findById(id).get();
        if (existHotel != null) {
           return ResponseEntity.ok().body(Map.of("status", "edit_success","hotel",existHotel));
        } 

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "edit_unsuccess"));
    }

    @PostMapping("/updateHotel")
    public ResponseEntity<?> updateHotel(@RequestParam("idHotel") String idHotel,
            @RequestParam("hotelName") String hotelName,
            @RequestParam("location") String location,
            @RequestParam("address") String address,
            @RequestParam("price") int price,
            @RequestParam("stars") int star,
            @RequestParam("image") MultipartFile image,
            @RequestParam("description") String description) {

        Hotel existHotel = hotelRepository.findById(idHotel).get();
                System.out.println(image);
        
        String imageFileName = FileStorageUtil.saveFile(image, ASSETS_IMAGES_DIR);
        if(imageFileName != null){
            System.out.println(imageFileName);
            existHotel.setImage(imageFileName);
        }

        if (existHotel != null) {
            existHotel.setHotelName(hotelName);
            existHotel.setLocation(location);
            existHotel.setAddress(address);
            existHotel.setStar(star);
            existHotel.setPrice(price);
            existHotel.setDescription(description);
            hotelRepository.save(existHotel);
            
            return ResponseEntity.ok().body(Map.of("status", "Save_Success"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Un Successfully"));
    }

}
