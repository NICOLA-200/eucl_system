package com.eucl.example.meter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meter")
public class MeterController {
    private final MeterService meterService;

    @PostMapping("/create")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<MeterResponse> createMeter(@Valid @RequestBody MeterRequest dto) throws Exception {
        MeterResponse response = meterService.create(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<MeterResponse> update(@PathVariable("id") Integer id, @Valid @RequestBody MeterRequest dto) throws Exception {
        MeterResponse response = meterService.updateMeter(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all/mine")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<MeterResponse> getMyMeters(Authentication connectedUser){
        MeterResponse response = meterService.getMyMeters(connectedUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<MeterResponse> getAllMeters(){
        MeterResponse response = meterService.getAllMeters();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<MeterResponse> getMeter(@PathVariable("id")Integer id) throws ChangeSetPersister.NotFoundException {
        MeterResponse response = meterService.getMeterById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<MeterResponse> deleteMeter(@PathVariable("id")Integer id) throws ChangeSetPersister.NotFoundException {
        MeterResponse response = meterService.deleteMeter(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
