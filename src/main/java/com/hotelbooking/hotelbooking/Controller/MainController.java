package com.hotelbooking.hotelbooking.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hotelbooking.hotelbooking.Entity.Account;
import com.hotelbooking.hotelbooking.Entity.Hotel;
import com.hotelbooking.hotelbooking.Repository.HotelRepo;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @Autowired
    HotelRepo hotelRepo;

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

    @GetMapping("/hotel")
    public String hotel(@RequestParam("id") String id, Model model) {
        Hotel hotel = hotelRepo.findById(id).orElse(null);
        model.addAttribute("hotel", hotel);
        return "User_UI/hotel-room.html";
    }

    @GetMapping("room")
    public String room(@RequestParam("id") String id) {
        return "User_UI/room.html";
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
    public String services() {
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

}
