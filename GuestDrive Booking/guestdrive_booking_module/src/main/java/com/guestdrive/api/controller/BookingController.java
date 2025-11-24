package com.guestdrive.api.controller;

import com.guestdrive.api.model.Booking;
import com.guestdrive.api.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/booking")
public class BookingController {
    @Autowired
    private BookingRepository bookingRepository;
    @GetMapping("/viewBookings")
    public List<Booking> viewBookings() {
        return bookingRepository.findAll();
    }
    @GetMapping("/viewBookingDetails")
    public Optional<Booking> viewBookingDetails(@RequestParam Long bookingId) {
        return bookingRepository.findById(bookingId);
    }

    @DeleteMapping("/cancelBooking")
    public String cancelBooking(@RequestParam Long bookingId) {
        bookingRepository.deleteById(bookingId);
        return "Booking cancelled successfully.";
    }

    @PostMapping("/uploadCarPicsAtTripStart")
    public String uploadCarPicsAtTripStart(@RequestParam Long bookingId, @RequestParam MultipartFile image) throws IOException {
        String folder = "uploaded_images/";
        File dir = new File(folder);
        if (!dir.exists()) dir.mkdir();
        String path = folder + image.getOriginalFilename();
        image.transferTo(new File(path));

        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        booking.setTripStartImagePath(path);
        bookingRepository.save(booking);
        return "Car image uploaded successfully.";
    }

    @PostMapping("/startTrip")
    public String startTrip(@RequestParam Long bookingId,
                            @RequestParam String odometer,
                            @RequestParam Long userId,
                            @RequestParam String datetime) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        booking.setOdometer(odometer);
        booking.setStatus("OnTrip");
        bookingRepository.save(booking);
        return "Trip started for booking ID: " + bookingId;
    }
}
