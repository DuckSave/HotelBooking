package com.hotelbooking.hotelbooking.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotelbooking.hotelbooking.Entity.Account;
import com.hotelbooking.hotelbooking.Entity.Hotel;
import com.hotelbooking.hotelbooking.Entity.HotelBooking;
import com.hotelbooking.hotelbooking.Entity.Room;
import com.hotelbooking.hotelbooking.Repository.HotelRepo;
import com.hotelbooking.hotelbooking.Repository.RoomRepo;
import com.hotelbooking.hotelbooking.Service.BookingHotelService;
import com.hotelbooking.hotelbooking.Service.EmailSenderService;
import com.hotelbooking.hotelbooking.Service.HotelService;
import com.hotelbooking.hotelbooking.Service.RoomService;
import com.hotelbooking.hotelbooking.Service.SessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private BookingHotelService bookingService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelRepo hotelRepo;

    @Autowired
    private EmailSenderService mailService;

    @Autowired
    private SessionService sessionService;

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/register")
    public String register() {
        return "register.html";
    }

    @GetMapping("/forgotPass")
    public String repass() {
        return "repass.html";
    }

    @GetMapping("/index")
    public String index() {
        return "/User_UI/index.html";
    }

    @GetMapping("/tours")
    public String tour() {
        return "/User_UI/tours.html";
    }

    @GetMapping("/hotels")
    public String hotel(Model model, @RequestParam(value = "priceFrom", required = false) Integer priceFrom,
            @RequestParam(value = "priceTo", required = false) Integer priceTo,
            @RequestParam(value = "stars", required = false) List<Integer> stars,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "4") int size) {

        List<Hotel> hotels = new ArrayList<>();
        if (priceFrom != null && priceTo != null) {
            hotels = hotelRepo.findByPriceBetween(priceFrom, priceTo);
            if (hotels.isEmpty()) {
                model.addAttribute("message", "No hotels found in the given price range");
            }
        } else if (stars != null && !stars.isEmpty()) {
            hotels = hotelRepo.findByStarIn(stars);
        } else if (address != null && !address.isEmpty()) {
            Hotel hotel = hotelRepo.findHotelByLocationAndAddress(location, address);
            if (hotel != null) {
                hotels.add(hotel);
            }
        } else if (location != null && !location.isEmpty()) {
            hotels = hotelRepo.findListHotelByLocation(location);
        } else {
            hotels = hotelRepo.findAll();
        }
        // Phân trang kết quả
        Page<Hotel> hotelPage = paginateHotels(hotels, page, size);
        // Add checkedStars to the model to mark checkboxes as checked
        model.addAttribute("checkedStars", stars);
        model.addAttribute("listHotel", hotelPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", hotelPage.getTotalPages());
        model.addAttribute("size", size);

        return "/User_UI/hotels";

    }

    // @GetMapping("/hotels")
    // public String hotels(Model model,
    //         @RequestParam(value = "page", defaultValue = "1") int page,
    //         @RequestParam(value = "size", defaultValue = "4") int size) {

    //     List<Hotel> hotels = hotelRepo.findAll();
    //     Page<Hotel> hotelPage = paginateHotels(hotels, page, size);

    //     model.addAttribute("listHotel", hotelPage.getContent());
    //     model.addAttribute("currentPage", page);
    //     model.addAttribute("totalPages", hotelPage.getTotalPages());
    //     model.addAttribute("size", size);
    //     return "/User_UI/hotels";
    // }

    // Phương thức tìm kiếm khách sạn theo giá
    // @GetMapping("/hotels/price")
    // public String filterByPrice(Model model,
    //         @RequestParam(value = "priceFrom") Integer priceFrom,
    //         @RequestParam(value = "priceTo") Integer priceTo,
    //         @RequestParam(value = "page", defaultValue = "1") int page,
    //         @RequestParam(value = "size", defaultValue = "4") int size) {

    // @GetMapping("/searchHotels")
    // public String searchHotel(@RequestParam("location") String location, Model model) {
    //     List<Hotel> listHotel = hotelRepo.findListHotelByLocation(location);
    //     model.addAttribute("listHotel", listHotel);
    //     return "/User_UI/hotels.html";
    //     List<Hotel> hotels = hotelRepo.findByPriceBetween(priceFrom, priceTo);
    //     if (hotels.isEmpty()) {
    //         model.addAttribute("message", "No hotels found in the given price range");
    //     }
    //     Page<Hotel> hotelPage = paginateHotels(hotels, page, size);

    //     model.addAttribute("listHotel", hotelPage.getContent());
    //     model.addAttribute("currentPage", page);
    //     model.addAttribute("totalPages", hotelPage.getTotalPages());
    //     model.addAttribute("size", size);

    //     return "/User_UI/hotels";
    // }

    // Phương thức tìm kiếm khách sạn theo sao
    @GetMapping("/hotels/stars")
    public String filterByStars(Model model,
            @RequestParam(value = "stars") List<Integer> stars,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "4") int size) {

        List<Hotel> hotels = hotelRepo.findByStarIn(stars);
        Page<Hotel> hotelPage = paginateHotels(hotels, page, size);

        model.addAttribute("checkedStars", stars);
        model.addAttribute("listHotel", hotelPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", hotelPage.getTotalPages());
        model.addAttribute("size", size);

        return "/User_UI/hotels";
    }

    // Phương thức tìm kiếm khách sạn theo địa chỉ và địa điểm
    @GetMapping("/hotels/addressAndLocation")
    public String filterByAddress(Model model,
            @RequestParam(value = "location") String location,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "4") int size) {
        List<Hotel> hotels = new ArrayList<>();
        if (address != null && !address.isEmpty()) {
            Hotel hotel = hotelRepo.findHotelByLocationAndAddress(location, address);
            if (hotel != null) {
                hotels.add(hotel);
            }
        } else if (location != null && !location.isEmpty()) {
            hotels = hotelRepo.findListHotelByLocation(location);
        }
        Page<Hotel> hotelPage = paginateHotels(hotels, page, size);

        model.addAttribute("listHotel", hotelPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", hotelPage.getTotalPages());
        model.addAttribute("size", size);

        return "/User_UI/hotels";
    }

    // Phương thức phân trang
    private Page<Hotel> paginateHotels(List<Hotel> hotels, int page, int size) {
        int start = Math.min((page - 1) * size, hotels.size());
        int end = Math.min(start + size, hotels.size());
        List<Hotel> paginatedList = hotels.subList(start, end);
        System.out.println(start);
        System.out.println(end);
        return new PageImpl<>(paginatedList, PageRequest.of(page - 1, size), hotels.size());

    }

    @GetMapping("/hotel")
    public String room(@RequestParam("id") String id, Model model) {
        Hotel hotel = hotelRepo.findById(id).orElse(null);
        model.addAttribute("hotel", hotel);
        List<Room> rooms = roomService.getRoomsByHotelId(hotel.getId());
        model.addAttribute("listRoom", rooms);
        return "User_UI/hotel-room.html";
    }

    @GetMapping("/contact")
    public String contact() {
        return "/User_UI/contact.html";
    }

    @GetMapping("/about")
    public String about() {
        return "/User_UI/about.html";
    }

    @GetMapping("/blog")
    public String blog() {
        return "/User_UI/blog.html";
    }

    @GetMapping("/services")
    public String services(Model model, HttpSession seesion,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "1") int size) {
        Account account = (Account) sessionService.getSession("account", seesion);
        HotelBooking booking = bookingService.getBooking(account);
        model.addAttribute("bookings", booking);
        return "/User_UI/services.html";
    }

    @GetMapping("/profile")
    public String showProfile(HttpServletRequest request, Model model) {
        Account account = (Account) request.getSession().getAttribute("account");
        if (account != null) {
            model.addAttribute("account", account);
            return "profile"; // Render profile.html
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/booking")
    public String booking(@RequestParam("id") String roomId, Model model) {
        Room room = roomService.getRoomById(roomId);
        Hotel hotel = hotelRepo.findById(room.getHotelId()).get();
        model.addAttribute("room", room);
        model.addAttribute("hotel", hotel);
        return "/User_UI/bookingForm.html";
    }

    @GetMapping("/admin/hotel")
    public String addHotel() {
        return "/Admin_UI/adminHotel.html";
    }

    @GetMapping("admin/hotel/room")
    public String addRoom(Model model) {
        List<Hotel> listHotel = hotelRepo.findAll();
        model.addAttribute("listHotel", listHotel);
        return "/Admin_UI/addRoom.html";
    }



}
