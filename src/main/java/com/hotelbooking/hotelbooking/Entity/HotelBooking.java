package com.hotelbooking.hotelbooking.Entity;


import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.hotelbooking.hotelbooking.Class.BookDetail;
import com.hotelbooking.hotelbooking.Class.CheckInTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelBooking {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private int numberOfGuests;
    private BookDetail bookDetail;
    private CheckInTime checkInTime;
    private boolean isPayment;
    
}
