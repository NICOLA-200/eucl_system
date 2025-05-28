package com.eucl.example.meter;

import com.eucl.example.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "meter")
@AllArgsConstructor
@NoArgsConstructor
public class Meter  {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true)
    private int meterNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
