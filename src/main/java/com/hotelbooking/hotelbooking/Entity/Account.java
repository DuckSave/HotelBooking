package com.hotelbooking.hotelbooking.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
    private boolean role;
    private String bookingId;
}
