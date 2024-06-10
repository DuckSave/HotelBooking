package com.hotelbooking.hotelbooking.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hotelbooking.hotelbooking.Class.BookingReportDTO;
import com.hotelbooking.hotelbooking.Service.ReportService;

@Controller
public class ReportController {
    
    @Autowired
    ReportService reportService;


    
    @GetMapping("/admin/reports")
    public String reports() {
        return "Admin_UI/reports";
    }

    @PostMapping("/admin/booking-report")
    public ResponseEntity<?> bookingReport(@RequestBody Map<String, String> payload) {
        List<BookingReportDTO> bookingReportDTO = reportService.getBookingReportByForm(payload);
        System.out.println(bookingReportDTO);
        return ResponseEntity.ok(bookingReportDTO);
    }
}
