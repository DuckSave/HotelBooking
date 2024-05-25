package com.hotelbooking.hotelbooking.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
    private String id;
    private String hotelName;
    private String location;
    private String address;
    private int star;
    private int price;
    private String image;
    private String description;
}
