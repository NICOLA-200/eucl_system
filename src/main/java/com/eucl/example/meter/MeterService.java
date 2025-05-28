package com.eucl.example.meter;

import com.eucl.example.user.User;
import com.eucl.example.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MeterService {
    private final UserRepository userRepository;
    private final MeterRepository meterRepository;



    public MeterResponse create(MeterRequest dto) throws Exception {
        // Check if meter number already exists
        if(meterRepository.existsByMeterNumber(Integer.parseInt(dto.getMeterNumber()))) {
            throw new Exception(String.format("Meter number %s already exists.", dto.getMeterNumber()));
        }

        // Get the user or throw exception if not found
        User user = userRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new NotFoundException(
                ));

        // Create and save the meter
        Meter meter = new Meter();
        meter.setMeterNumber(Integer.parseInt(dto.getMeterNumber()));
        meter.setUser(user);
        Meter savedMeter = meterRepository.save(meter);

        // Update user's meters list
        if(user.getMeters() == null) {
            user.setMeters(new ArrayList<>());
        }
        user.getMeters().add(savedMeter);
        userRepository.save(user);

        return new MeterResponse(savedMeter, "Meter created successfully.");
    }


    public MeterResponse getMyMeters(Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        return new MeterResponse(meterRepository.findAllByUser(user), null);
    }


    public MeterResponse getAllMeters() {
        return new MeterResponse(meterRepository.findAll(), null);
    }


    public MeterResponse getMeterById(Integer id) throws NotFoundException {
        return new MeterResponse(meterRepository.findById(id).orElseThrow(() -> new NotFoundException()), null);
    }


    public MeterResponse updateMeter(Integer id, MeterRequest dto) throws Exception {
        Meter meter = meterRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());

        if(meterRepository.existsByMeterNumber(Integer.parseInt(dto.getMeterNumber()))) {
            throw new Exception(String.format("Meter number %s already exists.", dto.getMeterNumber()));
        }

            meter.setMeterNumber(Integer.parseInt(dto.getMeterNumber()));


        Meter m = meterRepository.save(meter);

        return new MeterResponse<>(m, "Meter updated successfully ✅");
    }



    public MeterResponse deleteMeter(Integer id) throws NotFoundException {
        if(!meterRepository.existsById(id))throw new NotFoundException();
        meterRepository.deleteById(id);
        return new MeterResponse(null, String.format("Meter %s deleted successfully.", id));
    }
}
