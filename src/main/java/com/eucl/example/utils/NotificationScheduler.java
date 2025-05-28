package com.eucl.example.utils;

import com.eucl.example.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final NotificationService notificationService;

    @Scheduled(fixedRate = 600000) // Every 60 seconds
    public void scheduleTokenCheck() {
        notificationService.checkExpiringTokens();
    }
}
