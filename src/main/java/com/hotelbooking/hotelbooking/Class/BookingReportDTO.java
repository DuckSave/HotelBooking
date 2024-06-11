package com.hotelbooking.hotelbooking.Class;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class BookingReportDTO {
    private String fullName;
    private String email;
    private String HotelName;
    private String roomType;
    private LocalDate arrivalDate;
    private LocalDate departureDate;
    private long totalPrice;
    private boolean isPayment;

}
