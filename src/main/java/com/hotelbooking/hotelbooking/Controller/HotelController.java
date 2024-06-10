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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hotelbooking.hotelbooking.Entity.Hotel;
import com.hotelbooking.hotelbooking.Repository.HotelRepo;
import com.hotelbooking.hotelbooking.Service.HotelService;
import com.hotelbooking.hotelbooking.Utils.FileStorageUtil;

import ch.qos.logback.core.model.Model;


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
            return ResponseEntity.ok().body(Map.of("message", "Detele_Succes"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Detele_Unsucces", "Erro", "500"));
        
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
    public ResponseEntity<?> updateHotel(@RequestBody Map<String, String> payload) {

        String id = payload.get("idHotel");
        System.out.println(id);
        String hotelName = payload.get("hotelName");
        String location = payload.get("location");
        String address = payload.get("address");
        int star = Integer.parseInt(payload.get("star"));
        int price = Integer.parseInt(payload.get("price"));
        String description = payload.get("description");
        String image = payload.get("image");
        Optional<Hotel> existHotel = hotelRepository.findById(id);
        System.out.println(hotelName);
        if (existHotel != null) {
            Hotel existingHotel = existHotel.get();
            // Update hotel information
            existingHotel.setHotelName(hotelName);
            existingHotel.setLocation(location);
            existingHotel.setAddress(address);
            existingHotel.setStar(star);
            existingHotel.setPrice(price);
            existingHotel.setDescription(description);
            existingHotel.setImage(image);
            // Save the updated hotel
            hotelRepository.save(existingHotel);
            
            return ResponseEntity.ok().body(Map.of("status", "Save_Success"));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Save_Unseccess", "Erro", "Erro"));
    }
}
