package com.hotelbooking.hotelbooking.Entity;


import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.hotelbooking.hotelbooking.Class.BookingDetail;
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
    private List<BookingDetail> bookingDetail;
}
