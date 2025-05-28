package com.eucl.example.purchasedToken;

import com.eucl.example.exception.BadRequestException;
import com.eucl.example.exception.ResourceNotFoundException;
import com.eucl.example.meter.Meter;
import com.eucl.example.meter.MeterRepository;
import com.eucl.example.meter.MeterResponse;
import com.eucl.example.utils.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final PurchasedTokenRepository TokenRepository;
    private final MeterRepository meterRepository;



    public MeterResponse purchase(TokenRequest dto) {
        if(dto.getAmount() < 100){
            throw new BadRequestException("Amount must be greater than 100");
        }

        String meterNumStr = String.valueOf(dto.getMeterNumber());
        if (!meterNumStr.matches("^\\d{6}$")) {
            throw new BadRequestException("Meter number must be exactly 6 digits!");
        }

        if (!meterRepository.existsByMeterNumber(dto.getMeterNumber())) {
            throw new BadRequestException("Meter number does not exist!");
        }

        // get the amount and generate the token and the token will be containing the days of the token
        int days = dto.getAmount() / 100;
        if (days > 365 * 5) {
            throw new BadRequestException("You can't purchase electricity for more than 5 years! 😮");
        }

        String rawToken = Utility.generateToken();

        PurchasedToken token = new PurchasedToken();
        token.setMeterNumber(dto.getMeterNumber());
        token.setToken(rawToken);
        token.setStatus(ETokenStatus.NEW);
        token.setTokenValueDays(days);
        token.setPurchasedDate(LocalDateTime.now());
        token.setAmount(dto.getAmount());

        TokenRepository.save(token);

        // Format the token for readability
        String formattedToken = Utility.formatToken(rawToken);

        return new MeterResponse(formattedToken, "Token purchased successfully for " + days + " day(s)");
    }


    public MeterResponse validateToken(String token) {
        String rawToken = token.replace("-", "");

        PurchasedToken purchasedToken = TokenRepository.findByTokenAndStatus(rawToken, ETokenStatus.NEW)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found or already used"));

        purchasedToken.setStatus(ETokenStatus.USED);
        TokenRepository.save(purchasedToken);

        return new MeterResponse(purchasedToken.getTokenValueDays(),
                "Token is valid. Lights for " + purchasedToken.getTokenValueDays() + " day(s)");
    }

    public MeterResponse getTokensByMeter(Integer meterId) {
        Meter meter = meterRepository.findById(meterId)
                .orElseThrow(() -> new ResourceNotFoundException("Meter with ID " + meterId + " not found"));

        List<PurchasedToken> tokens = TokenRepository.findByMeterNumber(meter.getMeterNumber());

        List<String> formattedTokens = tokens.stream()
                .map(t -> Utility.formatToken(t.getToken()) + " (" + t.getTokenValueDays() + " days)")
                .toList();

        return new MeterResponse(formattedTokens, "Tokens for this meter");
    }



    public MeterResponse getToken(Integer id) throws ChangeSetPersister.NotFoundException {
        PurchasedToken token = TokenRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        return new MeterResponse(
                Utility.formatToken(token.getToken()),
                "Token for meter " + token.getMeterNumber()
        );
    }
}
