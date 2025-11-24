package com.guestdrive.api.repository;
import com.guestdrive.api.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByCity(String city);
}
