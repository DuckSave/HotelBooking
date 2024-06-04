package com.hotelbooking.hotelbooking.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.hotelbooking.hotelbooking.Class.BookingDetail;
import com.hotelbooking.hotelbooking.Entity.Account;
import com.hotelbooking.hotelbooking.Entity.HotelBooking;
import com.hotelbooking.hotelbooking.Entity.Room;
import com.hotelbooking.hotelbooking.Repository.HotelBookingRepo;

@Service
public class BookingHotelService {

    @Autowired
    private HotelBookingRepo bookingRepo;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoomService roomService;

    public HotelBooking getBooking(Account account) {
        Optional<HotelBooking> optionalBooking = bookingRepo.findById(account.getBookingId());
        return optionalBooking.orElse(null);
    }

    public Page<HotelBooking> getBookings(int page, int size) {
        return bookingRepo.findAll(PageRequest.of(page, size));
    }

    public HotelBooking createBooking(Map<String, String> payload, Account account) {
        HotelBooking booking = getBooking(account);

        if (booking == null) {
            booking = new HotelBooking();
            booking.setId(account.getBookingId());
        }

        List<BookingDetail> bookingDetailList = booking.getBookingDetail();

        if (bookingDetailList == null) {
            bookingDetailList = new ArrayList<>();
        }
        BookingDetail newBookingDetail = new BookingDetail();
        newBookingDetail.setFirstName(payload.get("firstName"));
        newBookingDetail.setLastName(payload.get("lastName"));
        newBookingDetail.setEmail(payload.get("email"));
        newBookingDetail.setNumberOfGuests(Integer.parseInt(payload.get("guests")));
        newBookingDetail.setArrivalDate(LocalDate.parse(payload.get("arrivalDate")));
        newBookingDetail.setArrivalTime(LocalTime.parse(payload.get("arrivalTime")));
        newBookingDetail.setDepartureDate(LocalDate.parse(payload.get("departureDate")));
        newBookingDetail.setDepartureTime(LocalTime.parse(payload.get("departureTime")));

        Room room = roomService.getRoomById(payload.get("roomId"));
        room.setBooking(true); // Cập nhật trạng thái phòng
        newBookingDetail.setRoom(room);

        bookingDetailList.add(newBookingDetail);
        booking.setBookingDetail(bookingDetailList);

        roomService.updateRoom(room); // Lưu trạng thái phòng đã được cập nhật
        return bookingRepo.save(booking);
    }
}
