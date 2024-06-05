package com.hotelbooking.hotelbooking.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import jakarta.servlet.http.HttpServletRequest;

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
    public String hotel(Model model) {
        List<Hotel> listHotel = hotelRepo.findAll();
        model.addAttribute("listHotel", listHotel);
        return "/User_UI/hotels.html";
    }

    @GetMapping("/searchHotels")
    public String searchHotel(@RequestParam("location") String location, Model model) {
        List<Hotel> listHotel = hotelRepo.findListHotelByLocation(location);
        model.addAttribute("listHotel", listHotel);
        return "/User_UI/hotels.html";
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
    public String services(Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "1") int size) {
        Page<HotelBooking> bookingPage = bookingService.getBookings(page, size);
        model.addAttribute("bookings", bookingPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookingPage.getTotalPages());
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
        return "/Admin_UI/addHotel.html";
    }

    @GetMapping("admin/hotel/room")
    public String addRoom(Model model) {
        List<Hotel> listHotel = hotelRepo.findAll();
        model.addAttribute("listHotel", listHotel);
        return "/Admin_UI/addRoom.html";
    }

    // @GetMapping("/sendMail")
    // public ResponseEntity<?> sendMail() {
    // HotelBooking booking = bookingService.getBooking("665d8e4945db4e4bb4f07446");
    // mailService.sendEmailToBookingPerson(booking);
    // Map<String, String> map = new HashMap<String, String>();
    // map.put("status", "SUCCESS");
    // return ResponseEntity.ok().body(map);
    // }

    @GetMapping("/cart")
    public String getBookings(Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "1") int size) {
        Page<HotelBooking> bookingPage = bookingService.getBookings(page, size);
        model.addAttribute("bookings", bookingPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookingPage.getTotalPages());
        return "/User_UI/cart.html";
    }

}
