package com.eucl.example.purchasedToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchasedTokenRepository extends JpaRepository<PurchasedToken, Integer> {
    Optional<PurchasedToken> findByTokenAndStatus(String token, ETokenStatus eTokenStatus);

    List<PurchasedToken> findByMeterNumber(int meterNumber);

    List<PurchasedToken> findByStatus(ETokenStatus eTokenStatus);

    @Query("SELECT pt FROM PurchasedToken pt WHERE pt.status = com.eucl.example.purchasedToken.ETokenStatus.NEW AND " +
            "FUNCTION('DATE_ADD', pt.purchasedDate, pt.tokenValueDays, 'DAY') BETWEEN " +
            "FUNCTION('DATE_ADD', CURRENT_TIMESTAMP, 5, 'HOUR') AND " +
            "FUNCTION('DATE_ADD', CURRENT_TIMESTAMP, 6, 'HOUR')")
    List<PurchasedToken> findTokensExpiringInFiveHours();
}
