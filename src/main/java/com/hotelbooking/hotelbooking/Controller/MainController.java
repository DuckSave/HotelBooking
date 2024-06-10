package com.hotelbooking.hotelbooking.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotelbooking.hotelbooking.Entity.Account;
import com.hotelbooking.hotelbooking.Entity.Hotel;
import com.hotelbooking.hotelbooking.Entity.HotelBooking;
import com.hotelbooking.hotelbooking.Entity.Room;
import com.hotelbooking.hotelbooking.Repository.HotelRepo;
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

    @Autowired
    private HotelService hotelService;

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
    public String hotel(Model model, @RequestParam(value = "page", defaultValue = "1") int page,HttpSession session) {
        List<Hotel> hotels = (List<Hotel>) sessionService.getSession("listHotel", session);
        if (hotels == null) {
            System.out.println("null");
            hotels = hotelService.findAllHotel();
        }
        hotelService.fillHotels(hotels, model, page);
        return "/User_UI/hotels.html";
    }

    @GetMapping("/hotels/price")
    public String filterByPrice(Model model, HttpSession session,
            @RequestParam(value = "priceFrom") int priceFrom,
            @RequestParam(value = "priceTo") int priceTo,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        List<Hotel> hotels = hotelService.findHotelByPrice(priceFrom, priceTo);
        sessionService.setSession("listHotel", hotels, session);
        return "redirect:/hotels";
    }

    @GetMapping("/hotels/stars")
    public String filterByStars(Model model,HttpSession session,
            @RequestParam(value = "stars") List<Integer> stars,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        List<Hotel> hotels = hotelRepo.findByStarIn(stars);
        sessionService.setSession("listHotel", hotels, session);
        return "redirect:/hotels";
    }

    @GetMapping("/hotels/addressAndLocation")
    public String filterByAddress(Model model,
            @RequestParam(value = "location") String location,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        List<Hotel> hotels = new ArrayList<>();
        if (address != null && !address.isEmpty()) {
            Hotel hotel = hotelRepo.findHotelByLocationAndAddress(location, address);
            if (hotel != null) {
                hotels.add(hotel);
            }
        } else if (location != null && !location.isEmpty()) {
            hotels = hotelRepo.findListHotelByLocation(location);
        }
        hotelService.fillHotels(hotels, model, page);
        return "/User_UI/hotels.html";
    }

    @GetMapping("/hotel")
    public String room(@RequestParam("id") String id, Model model) {
        Hotel hotel = hotelRepo.findById(id).orElse(null);
        if (hotel != null) {
            model.addAttribute("hotel", hotel);
            List<Room> rooms = roomService.getRoomsByHotelId(hotel.getId());
            model.addAttribute("listRoom", rooms);
        }
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
    public String services(Model model, HttpSession session,
            @RequestParam(name = "page", defaultValue = "0") int page) {
        Account account = (Account) sessionService.getSession("account", session);
        if (account != null) {
            HotelBooking booking = bookingService.getBooking(account);
            model.addAttribute("bookings", booking);
        }
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
        if (room != null) {
            Hotel hotel = hotelRepo.findById(room.getHotelId()).orElse(null);
            if (hotel != null) {
                model.addAttribute("room", room);
                model.addAttribute("hotel", hotel);
            }
        }
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
