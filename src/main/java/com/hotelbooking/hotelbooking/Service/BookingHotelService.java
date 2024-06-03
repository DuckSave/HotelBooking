package com.hotelbooking.hotelbooking.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.hotelbooking.hotelbooking.Entity.Account;
import com.hotelbooking.hotelbooking.Entity.HotelBooking;
import com.hotelbooking.hotelbooking.Entity.Room;
import com.hotelbooking.hotelbooking.Repository.HotelBookingRepo;


@Service
public class BookingHotelService {

    @Autowired
    private HotelBookingRepo bookingRepo;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoomService roomService;


    public HotelBooking getBooking(String id){
        return bookingRepo.findById(id).get();
    }

    public Page<HotelBooking> getBookings(int page, int size) {
        return bookingRepo.findAll(PageRequest.of(page, size));
    }

    public HotelBooking createBooking(Map<String, String> payload, Account account) {
    //     HotelBooking newBooking = new HotelBooking();
    //     BookDetail bookingDetail = new BookDetail();
    //     CheckInTime checkInTime = new CheckInTime(); 

    //     newBooking.setFirstName(payload.get("firstName"));
    //     newBooking.setLastName(payload.get("lastName"));
    //     newBooking.setEmail(payload.get("email"));
    //     newBooking.setNumberOfGuests(Integer.parseInt(payload.get("guests")));
        
    //     checkInTime.setArrivalTime(LocalTime.parse(payload.get("arrivalTime")));
    //     checkInTime.setDepartureTime(LocalTime.parse(payload.get("departureTime")));
    //     checkInTime.setArrivalDate(LocalDate.parse(payload.get("arrivalDate")));
    //     checkInTime.setDepartureDate(LocalDate.parse(payload.get("departureDate")));
    //     newBooking.setCheckInTime(checkInTime);
        
    //     Room room = roomService.getRoomById(payload.get("roomId"));
    //     room.setBooking(true); // Cập nhật trạng thái phòng
        
    //     bookingDetail.setAccount(account);
    //     bookingDetail.setRoom(room);
    //     newBooking.setBookDetail(bookingDetail);

    //     roomService.updateRoom(room); // Lưu trạng thái phòng đã được cập nhật
    //     return bookingRepo.save(newBooking);
    return null;
    }

}
