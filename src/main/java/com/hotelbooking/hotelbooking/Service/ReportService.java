package com.hotelbooking.hotelbooking.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelbooking.hotelbooking.Class.BookingDetail;
import com.hotelbooking.hotelbooking.Class.BookingReportDTO;
import com.hotelbooking.hotelbooking.Entity.Hotel;
import com.hotelbooking.hotelbooking.Entity.HotelBooking;
import com.hotelbooking.hotelbooking.Repository.HotelBookingRepo;

@Service
public class ReportService {

    @Autowired
    HotelBookingRepo hotelBookingRepo;

    @Autowired
    HotelService hotelService;

    public List<BookingReportDTO> getBookingReportByForm(Map<String, String> payload) {

        String hotelName = payload.get("hotelName");
        String roomType = payload.get("roomType");
        String checkIn = payload.get("checkIn");
        String checkOut = payload.get("checkOut");
        if (checkIn == null && checkOut == null && hotelName == null) {
            return getBookingReport();
        }


        return new ArrayList<>();
    }

    public List<BookingReportDTO> getBookingReport() {
        List<BookingReportDTO> bookingReport = convertData(hotelBookingRepo.findAll());
        return bookingReport;
    }

    private List<BookingReportDTO> convertData(List<HotelBooking> hotelBookings) {
        List<BookingReportDTO> bookingReport = new ArrayList<>();
        for (HotelBooking booking : hotelBookings) {
            List<BookingDetail> details = booking.getBookingDetail(); 
            if (details != null) {
                for(BookingDetail detail : details) {
                    Hotel hotel = hotelService.getHotelById(detail.getRoom().getHotelId());
                    if (hotel != null) {
                        BookingReportDTO bookingReportDTO = new BookingReportDTO();
                        bookingReportDTO.setFullName(detail.getFirstName() + " " + detail.getLastName());
                        bookingReportDTO.setEmail(detail.getEmail());
                        bookingReportDTO.setHotelName(hotel.getHotelName());
                        bookingReportDTO.setRoomType(detail.getRoom().getRoomType());
                        bookingReportDTO.setArrivalDate(detail.getArrivalDate());
                        bookingReportDTO.setDepartureDate(detail.getDepartureDate());
                        bookingReportDTO.setPayment(detail.isPayment());
                        bookingReportDTO.setTotalPrice(calulateTutalPrice(detail));
                        bookingReport.add(bookingReportDTO);
                    }
                }
            }
        }
        return bookingReport;
    }

    private long calulateTutalPrice(BookingDetail detail){
        long totalPrice = 0;
        int price = detail.getRoom().getPrice();
        int totalDay = (int) ChronoUnit.DAYS.between(detail.getArrivalDate(), detail.getDepartureDate());
        totalPrice = price * totalDay;
        return totalPrice;
    }
}
