package com.hotelbooking.hotelbooking.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import com.hotelbooking.hotelbooking.Entity.Hotel;
import com.hotelbooking.hotelbooking.Repository.HotelRepo;

@Service
public class HotelService {
    @Autowired
    private HotelRepo hotelRepo;


    public List<Hotel> findAllHotel(){
        return hotelRepo.findAll();
    }
    public Hotel getHotelById(String hotelId) {
        Hotel hotel = hotelRepo.findById(hotelId).get();
        return hotel == null ? null : hotel;
    }

    public void fillHotels(List<Hotel> hotelList, Model model, int page) {
         Page<Hotel> hotelPage = paginateHotels(hotelList, page, 6);
        // Add checkedStars to the model to mark checkboxes as checked
        model.addAttribute("listHotel", hotelPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", hotelPage.getTotalPages());
        model.addAttribute("size", 6);
    }

    public List<Hotel> findHotelByStar(List<Integer> star) {
        return hotelRepo.findByStarIn(star);
    }

        private Page<Hotel> paginateHotels(List<Hotel> hotels, int page, int size) {
        int start = Math.min((page - 1) * size, hotels.size());
        int end = Math.min(start + size, hotels.size());
        List<Hotel> paginatedList = hotels.subList(start, end);
        System.out.println(start);
        System.out.println(end);
        return new PageImpl<>(paginatedList, PageRequest.of(page - 1, size), hotels.size());

    }
    public Page<Hotel> searchHotelsByName(String keyword, Pageable pageable) {
        return hotelRepo.findByNameContainingIgnoreCase(keyword, pageable);
    }

    public Page<Hotel> getAllHotels(Pageable pageable) {
        return hotelRepo.findAll(pageable);
    }
    
    

    public List<Hotel> findHotelByPrice(int min, int max){
        return hotelRepo.findByPriceBetween(min, max);
    }
}
