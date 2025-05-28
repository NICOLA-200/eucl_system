package com.eucl.example.purchasedToken;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  TokenRequest {
    private int meterNumber;
    private int amount;
}
