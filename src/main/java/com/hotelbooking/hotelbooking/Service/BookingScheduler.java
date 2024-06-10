package com.hotelbooking.hotelbooking.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hotelbooking.hotelbooking.Class.BookingDetail;
import com.hotelbooking.hotelbooking.Entity.HotelBooking;
import com.hotelbooking.hotelbooking.Repository.HotelBookingRepo;

import java.time.LocalDate;
import java.util.List;

@Component
public class BookingScheduler {

    @Autowired
    private HotelBookingRepo bookingRepo;

    @Scheduled(cron = "0 0 0 * * *") // Chạy vào lúc 00:00 mỗi ngày
    public void checkAndCancelUnpaidBookings() {
        LocalDate now = LocalDate.now();
        List<HotelBooking> bookings = bookingRepo.findAll();

        for (HotelBooking booking : bookings) {
            List<BookingDetail> bookingDetails = booking.getBookingDetail();
            bookingDetails.removeIf(bookingDetail -> !bookingDetail.isPayment()
                    && bookingDetail.getBookingDate().plusDays(1).isBefore(now));

            // Lưu lại thay đổi
            bookingRepo.save(booking);
        }
    }
}
