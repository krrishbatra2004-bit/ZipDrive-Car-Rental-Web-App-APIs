package com.guestdrive.api.repository;
import com.guestdrive.api.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
