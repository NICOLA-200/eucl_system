package com.eucl.example.purchasedToken;

import com.eucl.example.meter.MeterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/purchase")
    public ResponseEntity<MeterResponse> purchaseToken(@RequestBody @Valid TokenRequest dto) {
        MeterResponse response = tokenService.purchase(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<MeterResponse> validateToken(@PathVariable String token) throws ChangeSetPersister.NotFoundException {
        MeterResponse response = tokenService.validateToken(token);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/meter/{meterId}")
    public ResponseEntity<MeterResponse> getTokensByMeter(@PathVariable Integer meterId) throws ChangeSetPersister.NotFoundException {
        MeterResponse response = tokenService.getTokensByMeter(meterId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeterResponse> getTokenById(@PathVariable Integer id) throws ChangeSetPersister.NotFoundException {
        MeterResponse response = tokenService.getToken(id);
        return ResponseEntity.ok(response);
    }
}
