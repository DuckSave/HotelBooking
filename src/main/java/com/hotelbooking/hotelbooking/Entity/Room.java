package com.hotelbooking.hotelbooking.Entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.hotelbooking.hotelbooking.Class.TimeBooking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    private String id;
    private String roomType;
    private int price;
    private List<String> images;
    private boolean isBooking;
    private String description;
    private String hotelId;
    private List<TimeBooking> timeBookings;
}
