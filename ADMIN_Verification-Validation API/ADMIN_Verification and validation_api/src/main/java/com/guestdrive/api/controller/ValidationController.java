package com.guestdrive.api.controller;

import com.guestdrive.api.model.Booking;
import com.guestdrive.api.model.Car;
import com.guestdrive.api.repository.BookingRepository;
import com.guestdrive.api.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validate")
public class ValidationController {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @PostMapping("/validateCar")
    public String validateCar(@RequestParam Long car_id, @RequestParam boolean value) {
        Car car = carRepository.findById(car_id).orElseThrow();
        car.setValidated(value);
        carRepository.save(car);
        return "Car validation updated";
    }
    @PostMapping("/validateGuest")
    public String validateGuest(@RequestParam Long booking_id, @RequestParam boolean value) {
        Booking booking = bookingRepository.findById(booking_id).orElseThrow();
        booking.setGuestValidated(value);
        bookingRepository.save(booking);
        return "Guest validation updated.";
    }
    @PostMapping("/verifyPayment")
    public String verifyPayment(@RequestParam Long booking_id, @RequestParam Long user_id) {
        Booking booking = bookingRepository.findById(booking_id).orElseThrow();
        if (!booking.isGuestValidated()) {
            return "Guest not validated!";
        }
        booking.setStatus("Booked");
        bookingRepository.save(booking);
        return "Payment verified. Booking confirmed.";
    }
}
