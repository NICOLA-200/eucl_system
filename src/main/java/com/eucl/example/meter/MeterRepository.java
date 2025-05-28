package com.eucl.example.meter;

import com.eucl.example.user.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeterRepository extends JpaRepository<Meter, Integer> {
    boolean existsByMeterNumber(@NotNull(message = "Meter number must be provided.") @Size(max = 6, min = 6, message = "Meter number must be exactly 6 digits") int meterNumber);

    List<Meter> findAllByUser(User user);
}
