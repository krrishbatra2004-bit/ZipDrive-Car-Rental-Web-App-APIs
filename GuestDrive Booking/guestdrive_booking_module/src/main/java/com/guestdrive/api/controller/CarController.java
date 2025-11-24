package com.guestdrive.api.controller;

import com.guestdrive.api.model.Booking;
import com.guestdrive.api.model.Car;
import com.guestdrive.api.repository.BookingRepository;
import com.guestdrive.api.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/car")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/searchCars")
    public List<Car> searchCars(@RequestParam String city) {
        return carRepository.findByCity(city);
    }

    @GetMapping("/viewaCar")
    public Optional<Car> viewaCar(@RequestParam Long carId) {
        return carRepository.findById(carId);
    }

    @PostMapping("/bookaCar")
    public String bookaCar(@RequestParam Long userId,
                           @RequestParam Long carId,
                           @RequestParam String start,
                           @RequestParam String end) {
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setCarId(carId);
        booking.setStart(start);
        booking.setEnd(end);
        booking.setStatus("Initiated");
        bookingRepository.save(booking);
        return "Booking initiated with ID: " + booking.getId();
    }
}
