package com.eucl.example.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findAllByMeterNumber(int meterId);
}
