package com.eucl.example.notification;

import com.eucl.example.meter.MeterResponse;
import com.eucl.example.purchasedToken.PurchasedToken;
import com.eucl.example.purchasedToken.PurchasedTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final PurchasedTokenRepository purchasedTokenRepository;

    public void checkExpiringTokens() {
        log.info("🔍 Checking for expiring tokens...");

        LocalDateTime now = LocalDateTime.now();
        List<PurchasedToken> tokens = purchasedTokenRepository.findAll();

        for (PurchasedToken token : tokens) {
            LocalDateTime expirationDate = token.getPurchasedDate().plusDays(token.getTokenValueDays());
            if (expirationDate.isAfter(now) && expirationDate.minusHours(24).isBefore(now)) {
                String message = "⚠️ Your token for meter " + token.getMeterNumber() + " is expiring soon!";

                Notification notification = new Notification();
                notification.setMeterNumber(token.getMeterNumber());
                notification.setMessage(message);
                notification.setIssuedDate(now);

                notificationRepository.save(notification);
                log.info("📨 Notification created for meter {}", token.getMeterNumber());
            }
        }
    }

    public MeterResponse getNotificationsByMeter(int meterId) {
        var notifications = notificationRepository.findAllByMeterNumber(meterId);
        return new MeterResponse(notifications, "Notifications for meter " + meterId);
    }

    public MeterResponse getAllNotifications() {
        var notifications = notificationRepository.findAll();
        return new MeterResponse(notifications, "All notifications for all meters");
    }
}
