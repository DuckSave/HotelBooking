package com.hotelbooking.hotelbooking.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotelbooking.hotelbooking.Class.BookingReportDTO;
import com.hotelbooking.hotelbooking.Entity.Hotel;
import com.hotelbooking.hotelbooking.Service.HotelService;
import com.hotelbooking.hotelbooking.Service.ReportService;

@Controller
public class ReportController {
    
    @Autowired
    ReportService reportService;

    @Autowired
    HotelService HotelService;


    
    @GetMapping("/admin/reports")
    public String reports(Model model) {
        List<Hotel> hotelList = HotelService.findAllHotel();
        model.addAttribute("listHotel", hotelList);
        return "Admin_UI/reports";
    }

    @PostMapping("/admin/booking-report")
    public ResponseEntity<?> bookingReport(@RequestBody Map<String, String> payload, Model model) {
        List<BookingReportDTO> bookingReportDTO = reportService.getBookingReportByForm(payload);


        if(bookingReportDTO != null) {
            model.addAttribute("listBooking", bookingReportDTO);
            return ResponseEntity.ok().body(Map.of("status", "BOOKING_REPORT_SUCCESS","bookings", bookingReportDTO));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "report fail"));
    }
}
