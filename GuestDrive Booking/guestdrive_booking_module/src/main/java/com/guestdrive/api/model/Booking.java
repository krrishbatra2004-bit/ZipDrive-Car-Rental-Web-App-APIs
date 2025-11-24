package com.guestdrive.api.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long carId;
    private String start;
    private String end;
    private String status;
    private String odometer;
    private String tripStartImagePath;
}
