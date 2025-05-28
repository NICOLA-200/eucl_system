package com.eucl.example.notification;

import com.eucl.example.meter.MeterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationsService;

    @GetMapping("/all/by-meter/{id}")
    public ResponseEntity<MeterResponse> getAllByMeterId(@PathVariable int id) {
        MeterResponse MeterResponse = notificationsService.getNotificationsByMeter(id);
        return ResponseEntity.ok(MeterResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<MeterResponse> getAllNotificationsForAdmin() {
        MeterResponse allNotifications = notificationsService.getAllNotifications();
        return ResponseEntity.ok(allNotifications);
    }

}