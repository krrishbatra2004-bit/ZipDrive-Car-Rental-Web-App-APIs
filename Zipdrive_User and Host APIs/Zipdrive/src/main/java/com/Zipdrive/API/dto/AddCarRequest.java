package com.Zipdrive.API.dto;
import lombok.Data;
@Data
public class AddCarRequest {
    private String make;
    private String model;
    private int yearOfMake;
    private int kmsDriven;
    private String rcNumber;
}
