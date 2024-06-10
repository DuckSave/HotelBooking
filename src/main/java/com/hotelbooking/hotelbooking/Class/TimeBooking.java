package com.hotelbooking.hotelbooking.Class;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeBooking {
    private LocalDate arrivalDate;
    private LocalDate departureDate;
}
