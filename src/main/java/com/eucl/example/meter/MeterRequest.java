package com.eucl.example.meter;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterRequest {

    @NotNull(message = "Meter number must be provided.")
    @Pattern(regexp = "^\\d{6}$", message = "Meter number must be exactly 6 digits!")
    private String meterNumber;

    @NotNull(message = "Customer id must be provided.")
    private Integer customerId;
}