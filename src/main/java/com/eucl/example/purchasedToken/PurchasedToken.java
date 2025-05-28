package com.eucl.example.purchasedToken;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "purchased_token")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PurchasedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "meter_number", length = 6)
    private int meterNumber;

    @Column(name = "token")
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_status")
    private ETokenStatus status;

    @Column(name = "token_value_days", length = 11)
    private Integer tokenValueDays;

    @Column(name = "purchased_date")
    private LocalDateTime purchasedDate;

    @Column(name = "amount", length = 11)
    private Integer amount;

}
