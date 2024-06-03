package com.hotelbooking.hotelbooking.Class;

import org.springframework.data.mongodb.core.mapping.Document;

import com.hotelbooking.hotelbooking.Entity.Account;
import com.hotelbooking.hotelbooking.Entity.Hotel;
import com.hotelbooking.hotelbooking.Entity.Room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDetail {
    private Account account;
    private Room room;
}
