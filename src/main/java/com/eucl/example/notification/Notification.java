package com.eucl.example.notification;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notification")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Notification  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "meter_number", length = 6)
    private int meterNumber;

    @Column(name = "message")
    private String message;

    @Column(name = "issued_date")
    private LocalDateTime issuedDate;
}
