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
import com.hotelbooking.hotelbooking.Class.TimeBooking;
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

    public boolean isAvaiable(String roomId, LocalDate checkIn, LocalDate checkOut) {
        Room room = roomService.getRoomById(roomId);

        if (room.getTimeBookings() == null) {
            room.setTimeBookings(new ArrayList<>());
        }

        for (TimeBooking timeBooking : room.getTimeBookings()) {
            LocalDate arrivalDate = timeBooking.getArrivalDate();
            LocalDate departureDate = timeBooking.getDepartureDate();

            if ((checkIn.isEqual(arrivalDate) || checkIn.isAfter(arrivalDate)) && checkIn.isBefore(departureDate)) {
                return false;
            }

            if (checkOut.isAfter(arrivalDate)
                    && (checkOut.isEqual(departureDate) || checkOut.isBefore(departureDate))) {
                return false;
            }

            if ((checkIn.isBefore(arrivalDate) || checkIn.isEqual(arrivalDate))
                    && (checkOut.isAfter(departureDate) || checkOut.isEqual(departureDate))) {
                return false;
            }
        }
        return true;
    }

    public HotelBooking createBooking(Map<String, String> payload, Account account) {
        String firstName = payload.get("firstName");
        String lastName = payload.get("lastName");
        String email = payload.get("email");
        int guests = Integer.parseInt(payload.get("guests"));
        LocalTime arrivalTime = LocalTime.parse(payload.get("arrivalTime"));
        LocalDate arrivalDate = LocalDate.parse(payload.get("arrivalDate"));
        LocalTime departureTime = LocalTime.parse(payload.get("departureTime"));
        LocalDate departureDate = LocalDate.parse(payload.get("departureDate"));
        String roomId = payload.get("roomId");

        if(!(isAvaiable(roomId, arrivalDate, departureDate))){
            return null;
        }


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
        newBookingDetail.setFirstName(firstName);
        newBookingDetail.setLastName(lastName);
        newBookingDetail.setEmail(email);
        newBookingDetail.setNumberOfGuests(guests);
        newBookingDetail.setBookingDate(LocalDate.now());
        newBookingDetail.setArrivalDate(arrivalDate);
        newBookingDetail.setArrivalTime(arrivalTime);
        newBookingDetail.setDepartureDate(departureDate);
        newBookingDetail.setDepartureTime(departureTime);

        Room room = roomService.getRoomById(payload.get("roomId"));

        // set room for bookingDetail
        newBookingDetail.setRoom(room);
        bookingDetailList.add(newBookingDetail);
        booking.setBookingDetail(bookingDetailList);

        // set timebooking for room
        List<TimeBooking> timeBookingList = room.getTimeBookings();
        if (timeBookingList == null) {
            timeBookingList = new ArrayList<>();
        }
        TimeBooking timeBooking = new TimeBooking();
        timeBooking.setArrivalDate(arrivalDate);
        timeBooking.setDepartureDate(departureDate);
        timeBookingList.add(timeBooking);
        room.setTimeBookings(timeBookingList);
        roomService.updateRoom(room);

        return bookingRepo.save(booking);
    }

    public HotelBooking getBookingById(String bookingId) {
        return bookingRepo.findById(bookingId).orElse(null);
    }

    public void setStatusPayment(String vnp_vnp_TxnRef) {
        HotelBooking booking = getBookingById(vnp_vnp_TxnRef);
        List<BookingDetail> details = booking.getBookingDetail();
        for (BookingDetail detail : details) {
            detail.setPayment(true);
        }
        bookingRepo.save(booking);
    }
}
